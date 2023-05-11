package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.domain.Boardgame;
import hr.algebra.tabletopshop.repository.BoardgameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class TabletopShopController {
    private BoardgameRepository bgRepo;

    @GetMapping("")
    public String getBoardgames(Model model) {
        model.addAttribute("boardgames", bgRepo.getAllBoardgames());
        model.addAttribute("newBg", new Boardgame());
        return "homePage/home";
    }

    @PostMapping("new")
    public String saveNewBoardgame(@ModelAttribute Boardgame newBg, Model model) {
        List<Boardgame> boardgames = bgRepo.getAllBoardgames();
        boardgames.add(newBg);
        model.addAttribute("boardgames", boardgames);
        model.addAttribute("newBg", newBg);
        return "homePage/home";
    }

}
