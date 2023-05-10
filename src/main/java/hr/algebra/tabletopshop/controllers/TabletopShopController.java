package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.domain.Boardgame;
import hr.algebra.tabletopshop.repository.BoardgameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TabletopShopController {
    private BoardgameRepository bgRepo;

    @GetMapping("")
    public String getBoardgames(Model model) {
        model.addAttribute("boardgames", bgRepo.getAllBoardgames());
        return "homePage/home";
    }

    @PostMapping("new")
    public String saveNewBoardgame(@ModelAttribute Boardgame newBg, Model model){
        System.out.println("New bg: " + newBg.getName());

        List<Boardgame> boardgames = (List<Boardgame>) model.getAttribute("boardgames");
        Objects.requireNonNull(boardgames).add(newBg);

        model.addAttribute("newBg", newBg);
        return "homePage/home";
    }

}
