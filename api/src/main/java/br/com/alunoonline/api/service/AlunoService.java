package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public List<Aluno> getAllAlunos(){
        return alunoRepository.findAll();
    }

    public Optional<Aluno> getAlunoById(Long id){
        return alunoRepository.findById(id);
    }

    public void newAluno(Aluno aluno){
        alunoRepository.save(aluno);
    }

    public void remAlunoById(Long id){
        alunoRepository.deleteById(id);
    }

    public void altAlunoById(Long id, Aluno aluno){
        aluno.setId(id);
        alunoRepository.save(aluno);
    }

}
