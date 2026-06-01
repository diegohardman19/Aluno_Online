package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.dtos.AtualizarNotasRequestDTO;
import br.com.alunoonline.api.dtos.HistoricoAlunoResponseDTO;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.service.MatriculaAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")
public class MatriculaAlunoController {

    @Autowired
    MatriculaAlunoService matriculaAlunoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void newMatricula(@RequestBody MatriculaAluno m) {
        matriculaAlunoService.newMatricula(m);
    }

    @PatchMapping("/trancar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void trancarMatricula(@PathVariable Long id) {
        matriculaAlunoService.trancarMatriculaAluno(id);
    }

    @PatchMapping("/atualizar-notas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarNotas(@PathVariable Long id,
                               @RequestBody AtualizarNotasRequestDTO dto) {
        matriculaAlunoService.altNotas(id, dto);
    }

    @GetMapping("/emitir-historico/{alunoId}")
    @ResponseStatus(HttpStatus.OK)
    public HistoricoAlunoResponseDTO emitirHistorico(
            @PathVariable Long alunoId) {
        return matriculaAlunoService.emitirHistorico(alunoId);
    }
}
