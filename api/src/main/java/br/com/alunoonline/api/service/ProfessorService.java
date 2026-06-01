package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    public List<Professor> getAllProfessores(){
        return professorRepository.findAll();
    }

    public Optional<Professor> getProfessorById(Long id){
        return professorRepository.findById(id);
    }

    public void newProfessor(Professor professor){
        professorRepository.save(professor);
    }

    public void remProfessorById(Long id){
        professorRepository.deleteById(id);
    }

    public void altProfessorById(Long id, Professor professor){
        professor.setId(id);
        professorRepository.save(professor);
    }

}
