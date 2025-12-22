package com.aihealthcare.aihealthcare.controller;

import com.aihealthcare.aihealthcare.dto.request.HealthRecordCreateRequest;
import com.aihealthcare.aihealthcare.dto.request.HealthRecordUpdateRequest;
import com.aihealthcare.aihealthcare.dto.response.HealthRecordResponse;
import com.aihealthcare.aihealthcare.service.HealthRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/health-records")
public class HealthRecordController{
    private final HealthRecordService healthRecordService;
    public HealthRecordController(HealthRecordService healthRecordService){
        this.healthRecordService = healthRecordService;
    }

    @PostMapping
    public ResponseEntity<HealthRecordResponse> create(@Valid @RequestBody HealthRecordCreateRequest request){
        HealthRecordResponse created = healthRecordService.create(request);

        return ResponseEntity.created(URI.create("/api.health-records/"+ created.getId())).body(created);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HealthRecordResponse> getById(@PathVariable Long id){
        HealthRecordResponse response = healthRecordService.getById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/by-date")
    public ResponseEntity<List<HealthRecordResponse>> getById(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ){
        List<HealthRecordResponse> response = healthRecordService.getByDate(from, to);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<HealthRecordResponse>> getAll(){
        List<HealthRecordResponse>list = healthRecordService.getAll();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthRecordResponse>update(
            @PathVariable Long id,
            @Valid @RequestBody HealthRecordUpdateRequest request
    ){
        HealthRecordResponse updated = healthRecordService.update(id, request);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        healthRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}