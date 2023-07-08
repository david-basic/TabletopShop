package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.domain.boardgames.Boardgame;
import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.repository.BoardgameRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/storeHome")
@AllArgsConstructor
@SessionAttributes("boardgames")
public class HomeController {
    private BoardgameRepository bgRepository;
    private CustomSpringEventPublisher customSpringEventPublisher;
    
    @GetMapping("/homePage.html")
    public String getStoreHomePage(Model model) {
        customSpringEventPublisher.publishCustomEven("Home page opened!");
        model.addAttribute("boardgames", bgRepository.getAllBoardgames());
        model.addAttribute("newBg", new Boardgame());
        return "homePage";
    }
    
    @PostMapping("/newBoardgame.html")
    public String saveNewBoardgame(@ModelAttribute Boardgame newBg, Model model) {
        model.addAttribute("newBg", newBg);
        bgRepository.saveNewBoardgame(newBg);
        return "redirect:/storeHome/homePage.html";
    }
    
    @GetMapping("/cleanSession.html")
    public String cleanSession(SessionStatus sessionStatus, HttpSession session) {
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/storeHome/homePage.html";
    }
}
