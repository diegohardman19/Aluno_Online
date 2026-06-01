package br.com.alunoonline.api.services;

import br.com.alunoonline.api.models.Aluno;
import br.com.alunoonline.api.models.MatriculaAluno;
import br.com.alunoonline.api.models.dtos.AtualizarNotasRequestDto;
import br.com.alunoonline.api.models.dtos.DisciplinasAlunoDto;
import br.com.alunoonline.api.models.dtos.HistoricoAlunoDto;
import br.com.alunoonline.api.repositories.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaAlunoService {

    static final Double GRADEAVGTOAPPROVE = 7.0;

    @Autowired
    MatriculaAlunoRepository repository;

    public MatriculaAluno create(MatriculaAluno matriculaAluno) {
        return repository.save(matriculaAluno);
    }

    public void updateGrades(AtualizarNotasRequestDto atualizarNotasRequestDto, Long matriculaAlunoId) {
        Optional<MatriculaAluno> matriculaAlunoToUpdate = repository.findById(matriculaAlunoId);

        if (matriculaAlunoToUpdate.isPresent()) {
            MatriculaAluno matriculaAluno = matriculaAlunoToUpdate.get();

            if (atualizarNotasRequestDto.getNota1() != null) {
                matriculaAluno.setNota1(atualizarNotasRequestDto.getNota1());
            }

            if (atualizarNotasRequestDto.getNota2() != null) {
                matriculaAluno.setNota2(atualizarNotasRequestDto.getNota2());
            }

            if (matriculaAluno.getNota1() != null && matriculaAluno.getNota2() != null) {
                double average = (matriculaAluno.getNota1() + matriculaAluno.getNota2()) / 2.0;

                if (average >= GRADEAVGTOAPPROVE) {
                    matriculaAluno.setStatus("APROVADO");
                } else {
                    matriculaAluno.setStatus("REPROVADO");
                }
            }

            repository.save(matriculaAluno);
        }
    }

    public void updateStatusToBreak(Long matriculaAlunoId) {
        Optional<MatriculaAluno> matriculaAlunoToUpdate = repository.findById(matriculaAlunoId);

        if (matriculaAlunoToUpdate.isPresent()) {
            MatriculaAluno matriculaAluno = matriculaAlunoToUpdate.get();
            String currentStatus = matriculaAluno.getStatus();

            if (currentStatus.equals("MATRICULADO")) {
                matriculaAluno.setStatus("TRANCADO");
                repository.save(matriculaAluno);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível trancar uma matrícula com status MATRICULADO.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matrícula não encontrada.");
        }
    }

    public HistoricoAlunoDto getHistoricoFromAluno(Long alunoId) {
        List<MatriculaAluno> matriculasDoAluno = repository.findByAlunoId(alunoId);

        if (!matriculasDoAluno.isEmpty()) {
            HistoricoAlunoDto historico = new HistoricoAlunoDto();

            historico.setNomeAluno(matriculasDoAluno.get(0).getAluno().getName());
            historico.setCursoAluno(matriculasDoAluno.get(0).getAluno().getCourse());
            List<DisciplinasAlunoDto> disciplinasList = new ArrayList<>();

            for (MatriculaAluno matricula: matriculasDoAluno) {
                DisciplinasAlunoDto disciplinasAlunoDto = new DisciplinasAlunoDto();

                disciplinasAlunoDto.setNomeDisciplina(matricula.getDisciplina().getName());
                disciplinasAlunoDto.setProfessorDisciplina(matricula.getDisciplina().getProfessor().getName());
                disciplinasAlunoDto.setNota1(matricula.getNota1());
                disciplinasAlunoDto.setNota2(matricula.getNota2());
                if ((matricula.getNota1() != null && matricula.getNota2() != null)) {
                    disciplinasAlunoDto.setMedia(matricula.getNota1() + matricula.getNota2() / 2);
                } else {
                    disciplinasAlunoDto.setMedia(null);
                }
                disciplinasAlunoDto.setStatus(matricula.getStatus());

                disciplinasList.add(disciplinasAlunoDto);
            }

            historico.setDisciplinasAlunoList(disciplinasList);

            return historico;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse aluno não possui matrículas.");
    }

}
