package hr.algebra.tabletopshop.repository;

import hr.algebra.tabletopshop.domain.boardgames.Boardgame;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BoardgameRepositoryMock implements BoardgameRepository {
    
    private List<Boardgame> bgList;
    
    public BoardgameRepositoryMock() {
        bgList = new ArrayList<>();
        
//        Boardgame game1 = new Boardgame(1, "Agricola", 2016, 8.0, 3.47, 120, new BigDecimal(50));
//        Boardgame game2 = new Boardgame(2, "7 Wonders", 2020, 7.9, 2.17, 30, new BigDecimal(60));
//        Boardgame game3 = new Boardgame(3, "Ankh", 2021, 7.9, 3.08, 90, new BigDecimal(250));
        Boardgame game1 = new Boardgame(1, "Agricola", 2016, 8.0, 3.47, 120, 50.00);
        Boardgame game2 = new Boardgame(2, "7 Wonders", 2020, 7.9, 2.17, 30, 60.00);
        Boardgame game3 = new Boardgame(3, "Ankh", 2021, 7.9, 3.08, 90, 250.00);
        
        bgList.add(game1);
        bgList.add(game2);
        bgList.add(game3);
    }
    
    @Override
    public List<Boardgame> getAllBoardgames() {
        return bgList;
    }
    
    @Override
    public void saveNewBoardgame(Boardgame boardgame) {
        bgList.add(boardgame);
    }
    
    @Override
    public List<Boardgame> filterBoardgames(Boardgame filter) {
        List<Boardgame> filteredList = getAllBoardgames();
        
        if (Optional.ofNullable(filter.getId()).isPresent()) {
            filteredList = filteredList.stream().filter(bg -> bg.getId().equals(filter.getId())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getName()).isPresent()) {
            filteredList = filteredList.stream().filter(bg -> bg.getName().equals(filter.getName())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getPublished()).isPresent()) {
            filteredList = filteredList.stream().filter(bg -> bg.getPublished().equals(filter.getPublished())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getRating()).isPresent()) {
            filteredList = filteredList.stream().filter(bg -> bg.getRating().equals(filter.getRating())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getWeight()).isPresent()) {
            filteredList = filteredList.stream().filter(bg -> bg.getWeight().equals(filter.getWeight())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getDuration()).isPresent()) {
            filteredList = filteredList.stream().filter(bg -> bg.getDuration().equals(filter.getDuration())).collect(Collectors.toList());
        }
        if (Optional.ofNullable(filter.getPrice()).isPresent()) {
            filteredList = filteredList.stream().filter(bg -> bg.getPrice().equals(filter.getPrice())).collect(Collectors.toList());
        }
        
        return filteredList;
        
    }
}
