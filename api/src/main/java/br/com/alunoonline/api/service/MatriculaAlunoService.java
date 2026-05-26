package br.com.alunoonline.api.service;

import br.com.alunoonline.api.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import br.com.alunoonline.api.dtos.AtualizarNotaRequestDTO;

@Service
public class MatriculaAlunoService {

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    private static final Double MEDIA_PARA_APROVAR = 7.0;

    // ═══════════════ ATUALIZAR NOTAS ═══════════════
    public void atualizarNotas(Long id, AtualizarNotaRequestDTO dto) {

        MatriculaAluno matricula =
                matriculaAlunoRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Matrícula não encontrada"));

        if (dto.getNota1() != null)
            matricula.setNota1(dto.getNota1());
        if (dto.getNota2() != null)
            matricula.setNota2(dto.getNota2());

        if (matricula.getNota1() != null
                && matricula.getNota2() != null) {
            Double media = (matricula.getNota1()
                    + matricula.getNota2()) / 2;
            matricula.setStatus(
                    media >= MEDIA_PARA_APROVAR
                            ? MatriculaAlunoStatusEnum.APROVADO
                            : MatriculaAlunoStatusEnum.REPROVADO);
        }

        matriculaAlunoRepository.save(matricula);
    }

    // ═══════════════ CRIAR MATRÍCULA ═══════════════
    public void criarMatricula(MatriculaAluno matriculaAluno) {
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    // ═══════════════ TRANCAR MATRÍCULA ═══════════════
    public void trancarMatricula(Long id) {

        MatriculaAluno matriculaAluno =
                matriculaAlunoRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Matrícula não encontrada"));

        if (matriculaAluno.getStatus()
                .equals(MatriculaAlunoStatusEnum.MATRICULADO)) {
            matriculaAluno.setStatus(MatriculaAlunoStatusEnum.TRANCADO);
            matriculaAlunoRepository.save(matriculaAluno);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Só é possível trancar com o status MATRICULADO");
        }
    }
}