package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.domain.Boardgame;
import hr.algebra.tabletopshop.dto.BoardgameDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class TabletopShopController {

    @GetMapping("/boardgames")
    public List<BoardgameDto> GetAllBoardgames() {
        return generateBoardgames();
    }

    @GetMapping("boardgames/{boardgameId}")
    public BoardgameDto getBoardgameById(@PathVariable String boardgameId) {
        return generateBoardgames().stream()
                .filter(bg -> bg.getId().equals(Integer.parseInt(boardgameId)))
                .toList().get(0);
    }

    private List<BoardgameDto> generateBoardgames() {
        Boardgame game1 = new Boardgame(
                1, "Agricola", 2016, 8.0, 3.47, 120, new BigDecimal(50)
        );
        Boardgame game2 = new Boardgame(
                2, "7 Wonders", 2020, 7.9, 2.17, 30, new BigDecimal(60)
        );
        Boardgame game3 = new Boardgame(
                3, "Ankh", 2021, 7.9, 3.08, 90, new BigDecimal(250)
        );

        List<BoardgameDto> boardgameDtoList = new ArrayList<>();
        boardgameDtoList.add(new BoardgameDto(game1));
        boardgameDtoList.add(new BoardgameDto(game2));
        boardgameDtoList.add(new BoardgameDto(game3));

        return boardgameDtoList;
    }
}
