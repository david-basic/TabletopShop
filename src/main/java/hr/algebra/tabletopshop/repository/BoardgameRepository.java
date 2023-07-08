package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.domain.boardgames.Boardgame;

import java.util.List;

public interface BoardgameRepository {
    List<Boardgame> getAllBoardgames();
    void saveNewBoardgame(Boardgame boardgame);
    
    List<Boardgame> filterBoardgames(Boardgame filter);

}
