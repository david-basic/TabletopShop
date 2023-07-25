package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.domain.boardgames.Boardgame;

import java.util.List;

public interface BoardgameValidationService {
    String validateDuplicateBoardgame(Boardgame boardgameToValidate, List<Boardgame> boardgames);
}
