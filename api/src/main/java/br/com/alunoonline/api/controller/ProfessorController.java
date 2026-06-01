package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> getAllProfessores(){
        return professorService.getAllProfessores();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Professor> getProfessorById(@PathVariable Long id){
        return professorService.getProfessorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void newProfessor(@RequestBody Professor professor){
        professorService.newProfessor(professor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remProfessorById(@PathVariable Long id){
        professorService.remProfessorById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void altProfessorById(@PathVariable Long id, @RequestBody Professor professor){
        professorService.altProfessorById(id, professor);
    }
}
