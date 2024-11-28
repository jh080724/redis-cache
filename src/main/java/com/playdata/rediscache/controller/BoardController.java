package com.playdata.rediscache.controller;

import com.playdata.rediscache.entity.Board;
import com.playdata.rediscache.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public List<Board> getBoards(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size) {

        return boardService.getBoards(page, size);
    }
}
