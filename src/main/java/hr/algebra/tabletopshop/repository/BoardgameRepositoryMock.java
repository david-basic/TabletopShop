package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.domain.Boardgame;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardgameRepositoryMock implements BoardgameRepository {
    private static List<Boardgame> bgList;

    public BoardgameRepositoryMock() {
        bgList = new ArrayList<>();

        Boardgame game1 = new Boardgame(
                1, "Agricola", 2016, 8.0, 3.47, 120, new BigDecimal(50)
        );
        Boardgame game2 = new Boardgame(
                2, "7 Wonders", 2020, 7.9, 2.17, 30, new BigDecimal(60)
        );
        Boardgame game3 = new Boardgame(
                3, "Ankh", 2021, 7.9, 3.08, 90, new BigDecimal(250)
        );

        bgList.add(game1);
        bgList.add(game2);
        bgList.add(game3);
    }

    @Override
    public List<Boardgame> getAllBoardgames() {
        return bgList;
    }
}
