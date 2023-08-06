package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.domain.boardgames.Boardgame;
import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.repository.BoardgameRepository;
import hr.algebra.tabletopshop.service.BoardgameValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@RequestMapping("/storeHome")
@AllArgsConstructor
@SessionAttributes("boardgames")
public class HomeController {
    private BoardgameRepository bgRepository;;
    private CustomSpringEventPublisher customSpringEventPublisher;
    private BoardgameValidationService boardgameValidationService;
    
    @GetMapping("/homePage.html")
    public String getStoreHomePage(Model model) {
        customSpringEventPublisher.publishCustomEven("Home page opened!");
        model.addAttribute("boardgames", bgRepository.getAllBoardgames());
        model.addAttribute("boardgame", new Boardgame());
        return "homePage";
    }
    
    @PostMapping("/newBoardgame.html")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveNewBoardgame(Model model, @ModelAttribute @Valid Boardgame boardgame, BindingResult bindingResult) {
        model.addAttribute("boardgame", boardgame);
        
        String duplicateError = boardgameValidationService.validateDuplicateBoardgame(boardgame, bgRepository.getAllBoardgames());
        
        if (!duplicateError.isEmpty()) {
            ObjectError error = new ObjectError("globalError", duplicateError);
            bindingResult.addError(error);
        }
        
        if (bindingResult.hasErrors()) {
            return "homePage";
        } else {
            bgRepository.saveNewBoardgame(boardgame);
            return "redirect:/storeHome/homePage.html";
        }
    }
    
    @GetMapping("/cleanSession.html")
    public String cleanSession(SessionStatus sessionStatus, HttpSession session) {
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/storeHome/homePage.html";
    }
    
    @GetMapping("/adminPage.html")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getStoreAdminPage() {
        return "adminPage";
    }
    
    @ResponseBody
    @GetMapping("/getBoardgameData")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getBoardgameData() throws InterruptedException {
        List<Boardgame> boardgames = bgRepository.getAllBoardgames();
        Thread.sleep(10000);
        return "{\"message\": \"Na skladištu imate " + boardgames.size() + " boardgamea.\"}";
    }
}
