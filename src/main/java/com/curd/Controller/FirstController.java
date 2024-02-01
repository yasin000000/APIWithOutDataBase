package com.curd.Controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.curd.Model.FirstModel;

@RestController
@RequestMapping("/api/firstmodels")
public class FirstController {

    private List<FirstModel> firstModels = new ArrayList<>();

    @GetMapping("/gest")
    public List<FirstModel> getAllFirstModels() {
        return firstModels;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FirstModel> getFirstModelById(@PathVariable Long id) {
        FirstModel firstModel = findFirstModelById(id);
        if (firstModel != null) {
            return ResponseEntity.ok(firstModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/sent")
    public ResponseEntity<FirstModel> createFirstModel(@RequestBody FirstModel firstModel) {
        firstModel.setId(generateFirstModelId());
        firstModels.add(firstModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(firstModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FirstModel> updateFirstModel(@PathVariable Long id, @RequestBody FirstModel updatedFirstModel) {
        FirstModel existingFirstModel = findFirstModelById(id);
        if (existingFirstModel != null) {
            existingFirstModel.setTitle(updatedFirstModel.getTitle());
            existingFirstModel.setCompleted(updatedFirstModel.isCompleted());
            return ResponseEntity.ok(existingFirstModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFirstModel(@PathVariable Long id) {
        FirstModel firstModel = findFirstModelById(id);
        if (firstModel != null) {
            firstModels.remove(firstModel);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private FirstModel findFirstModelById(Long id) {
        return firstModels.stream()
                .filter(firstModel -> firstModel.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private Long generateFirstModelId() {
        return firstModels.isEmpty() ? 1 : firstModels.get(firstModels.size() - 1).getId() + 1;
    }
}
