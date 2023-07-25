package hr.algebra.tabletopshop.service;

import hr.algebra.tabletopshop.domain.boardgames.Boardgame;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardgameValidationServiceImpl implements BoardgameValidationService {
    @Override
    public String validateDuplicateBoardgame(Boardgame boardgameToValidate, List<Boardgame> boardgames) {
        String message = "";
        
        for (Boardgame boardgame : boardgames) {
            if (boardgame.equals(boardgameToValidate)) {
                message = "Boardgame already exists!";
                break;
            }
        }
        
        return message;
    }
}
