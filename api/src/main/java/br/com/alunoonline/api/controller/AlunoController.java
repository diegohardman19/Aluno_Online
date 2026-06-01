package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Aluno> getAllAlunos(){
        return alunoService.getAllAlunos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Aluno> getAlunoById(@PathVariable Long id){
        return alunoService.getAlunoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void newAluno(@RequestBody Aluno aluno){
        alunoService.newAluno(aluno);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remAlunoById(@PathVariable Long id){
        alunoService.remAlunoById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void altAlunoById(@PathVariable Long id, @RequestBody Aluno aluno){
        alunoService.altAlunoById(id, aluno);
    }
}
