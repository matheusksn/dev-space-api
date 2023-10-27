package com.matheusksn.devspaceapi.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.matheusksn.devspaceapi.dtos.AvailableLabDTO;
import com.matheusksn.devspaceapi.entities.Lab;
import com.matheusksn.devspaceapi.entities.LabReservation;
import com.matheusksn.devspaceapi.entities.UserType;
import com.matheusksn.devspaceapi.repositories.LabRepository;
import com.matheusksn.devspaceapi.repositories.LabReservationRepository;

@Service
public class LabReservationService {

    @Autowired
    LabReservationRepository labReservationRepository;

    @Autowired
    LabService labService;

    @Autowired
    LabRepository labRepository;

    private static final String API_URL = "https://api-go-wash-efc9c9582687.herokuapp.com/api/pay-boleto";
    private static final String AUTHORIZATION_HEADER = "Authorization: Vf9WSyYqnwxXODjiExToZCT9ByWb3FVsjr";
    private static final String CONTENT_TYPE_HEADER = "Content-Type: application/json";
    private static final String COOKIE_HEADER = "Cookie: gowash_session=m6Y5t4HwextNyZIPR4uCOD97ebOoYusUfmRMwt06";

    public List<LabReservation> getAllLabReservations() {
        return labReservationRepository.findAll();
    }

    public LabReservation getLabReservationById(Long id) {
        return labReservationRepository.findById(id).orElse(null);
    }
    
    public boolean labStatus(Long labId, String date) {
    	return true;
    }

    public String createLabReservation(LabReservation labReservation) {
        if (!isLabAvailable(labReservation.getLab(), labReservation.getDate())) {
            return "Lab not available at the specified date and time.";
        }

        if (!labReservation.getLab().isActive()) {
            return "Lab is not active.";
        }

        UserType userType = labReservation.getUser().getUserType();

        if (userType != null && userType.getId() == 1) {
            // Se o userType for 1 (aluno), gera o boleto
            String boleto = generateBoleto(labReservation.getUser().getId());
            labReservation.setBoleto(boleto);

            // Envia o boleto para a API de pagamento e obtém o status
            String paymentStatus = processPaymentWithAPI(boleto);

            if ("approved".equals(paymentStatus)) {
                labReservation.setPaymentStatus(paymentStatus);
                labReservationRepository.save(labReservation);
                return "Lab reservation created. Payment approved.";
            } else {
                // O pagamento não foi aprovado, você pode tratar a situação apropriadamente
                return "Payment not approved. Please try again later.";
            }
        }

        // Se o userType não for 1 (ou não for aluno), a reserva é criada diretamente
        // Não é necessário gerar boleto ou processar pagamento
        // Outras regras de negócios para outros tipos de usuários podem ser implementadas aqui, se necessário

        // Retorna a resposta padrão para a reserva sem pagamento
        return "Lab reservation created.";
    }


    public String generateBoleto(Long userId) {
        // Gere um número aleatório de 8 dígitos
        String boletoNumber = generateRandomBoletoNumber();

        // Simule o processamento do boleto na API de pagamento
        String paymentStatus = processPaymentWithAPI(boletoNumber);

        if ("approved".equals(paymentStatus)) {
            return boletoNumber;
        } else {
            return "Tente novamente"; 
        }
    }

    private String generateRandomBoletoNumber() {
        Random random = new Random();
        int min = 10000000; // 8 dígitos
        int max = 99999999;
        int randomNumber = random.nextInt(max - min + 1) + min;
        return String.valueOf(randomNumber);
    }

    public String processPaymentWithAPI(String boletoNumber) {
        RestTemplate restTemplate = new RestTemplate();

        // Defina a URL da API de pagamento
        String apiUrl = "https://api-go-wash-efc9c9582687.herokuapp.com/api/pay-boleto";

        // Crie os cabeçalhos da solicitação
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Vf9WSyYqnwxXODjiExToZCT9ByWb3FVsjr");
        headers.set("Cookie", "gowash_session=m6Y5t4HwextNyZIPR4uCOD97ebOoYusUfmRMwt06");

        //  corpo da solicitação
        String requestBody = String.format("{\"boleto\":\"%s\",\"user_id\":2}", boletoNumber);

        //  entidade de solicitação
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
            System.out.println(response);

            if (response.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = response.getBody();
                System.out.println(jsonResponse);
                JSONObject json = new JSONObject(jsonResponse);

                try {
                    String status = json.getString("status");
                    if ("approved".equals(status)) {
                        return "approved";
                    } else {
                        return "not approved";
                    }
                } catch (JSONException e) {
                    return "not approved"; // O campo "status" não existe na resposta JSON
                }
            } else {
                return "not approved"; // Trate outros códigos de resposta, se necessário
            }
            
            //implementar tratativa de erros personalizados
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return "not approved";
        } catch (HttpServerErrorException e) {
            e.printStackTrace();
            return "not approved"; 
        } catch (Exception e) {
            e.printStackTrace();
            return "not approved";
        }
    }


    private boolean isLabAvailable(Lab lab, Date date) {
        return true; 
    }
    
    public List<AvailableLabDTO> getAvailableLabsWithAvailability(Date startDate, Date endDate) {
        List<Lab> allLabs = labRepository.findByIsActive(true);
        List<AvailableLabDTO> labsWithAvailability = new ArrayList<>();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.HOUR_OF_DAY, 8);
        startCalendar.set(Calendar.MINUTE, 0);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 0);

        for (Lab lab : allLabs) {
            boolean isLabAvailable = true;
            List<Date> availableTimes = new ArrayList<>();

            List<LabReservation> overlappingReservations = labReservationRepository.findOverlappingReservationsForLab(lab.getId(), startDate, endDate);

            if (!overlappingReservations.isEmpty()) {
                isLabAvailable = false;
            }

            Calendar reservationStart = Calendar.getInstance();
            reservationStart.setTime(startDate);

            Calendar reservationEnd = Calendar.getInstance();
            reservationEnd.setTime(endDate);

            if (reservationStart.before(startCalendar) || reservationEnd.after(endCalendar)) {
                isLabAvailable = false;
            }

            if (isLabAvailable) {
                AvailableLabDTO labWithAvailability = new AvailableLabDTO(lab, availableTimes);
                labsWithAvailability.add(labWithAvailability);
            }
        }
        return labsWithAvailability;
    }
}
