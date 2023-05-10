package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.domain.Boardgame;

import java.util.List;

public interface BoardgameRepository {
    List<Boardgame> getAllBoardgames();
}
