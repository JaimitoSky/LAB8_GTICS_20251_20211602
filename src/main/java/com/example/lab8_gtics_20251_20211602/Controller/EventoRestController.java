package com.example.lab8_gtics_20251_20211602.Controller;

import com.example.lab8_gtics_20251_20211602.Entity.Evento;
import com.example.lab8_gtics_20251_20211602.Repository.EventoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventoRestController {

    private final EventoRepository eventoRepository;

    @Value("${weatherapi.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // 1. Obtener eventos deportivos por ciudad
    @GetMapping("/eventos")
    public ResponseEntity<?> obtenerEventosDeportivos(@RequestParam String ciudad) {
        String url = "https://api.weatherapi.com/v1/sports.json?key=" + apiKey + "&q=" + ciudad;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !response.containsKey("sports")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron eventos deportivos.");
        }

        List<Map<String, Object>> eventos = (List<Map<String, Object>>) response.get("sports");

        // Filtrar próximos 7 días
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(7);

        List<Map<String, Object>> eventosFiltrados = eventos.stream()
                .filter(ev -> {
                    try {
                        String fecha = (String) ev.get("date");
                        LocalDate fechaEvento = LocalDate.parse(fecha);
                        return !fechaEvento.isBefore(hoy) && !fechaEvento.isAfter(limite);
                    } catch (Exception e) {
                        return false;
                    }
                }).collect(Collectors.toList());

        return ResponseEntity.ok(eventosFiltrados);
    }

    // 2. Obtener clima para fecha y ciudad
    @GetMapping("/clima")
    public ResponseEntity<?> obtenerClima(
            @RequestParam String ciudad,
            @RequestParam String fecha
    ) {
        String url = "https://api.weatherapi.com/v1/forecast.json?key=" + apiKey + "&q=" + ciudad + "&days=7";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response == null || !response.containsKey("forecast")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró información de clima.");
        }

        Map<String, Object> forecast = (Map<String, Object>) response.get("forecast");
        List<Map<String, Object>> forecastDays = (List<Map<String, Object>>) forecast.get("forecastday");

        for (Map<String, Object> day : forecastDays) {
            if (fecha.equals(day.get("date"))) {
                Map<String, Object> dayInfo = (Map<String, Object>) day.get("day");
                Map<String, Object> condition = (Map<String, Object>) dayInfo.get("condition");

                Map<String, Object> clima = new LinkedHashMap<>();
                clima.put("condition", condition.get("text"));
                clima.put("max_temp_c", dayInfo.get("maxtemp_c"));
                clima.put("min_temp_c", dayInfo.get("mintemp_c"));

                return ResponseEntity.ok(clima);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró clima para esa fecha.");
    }

    // 3. Guardar evento en BD
    @PostMapping("/eventos")
    public ResponseEntity<?> registrarEvento(@RequestBody @Valid Evento evento) {
        eventoRepository.save(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body("Evento registrado correctamente");
    }
}
