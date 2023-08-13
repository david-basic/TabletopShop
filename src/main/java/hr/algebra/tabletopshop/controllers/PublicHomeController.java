package hr.algebra.tabletopshop.controllers;

import hr.algebra.tabletopshop.publisher.CustomSpringEventPublisher;
import hr.algebra.tabletopshop.repository.mongodb.ItemRepositoryMongo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/public")
@AllArgsConstructor
@SessionAttributes("publicItems")
public class PublicHomeController {
//    private UserRepositoryMongo userRepositoryMongo;
//    private RoleRepositoryMongo roleRepositoryMongo;
    private ItemRepositoryMongo itemRepositoryMongo;
    private CustomSpringEventPublisher customSpringEventPublisher;
    
    @GetMapping("/home.html")
    public String openPublicHomePage(Model model) {
        customSpringEventPublisher.publishCustomEvent("Anonymous entered public home page!");
        model.addAttribute("publicItems", itemRepositoryMongo.findAll());
        //createItems(); // used for the first time to get all the items into mongodb
        //createUsers(); // used for the first time to get all the basic users into mongodb
        //createRoles(); // used for the first time to get all the basic roles into mongodb
        return "home";
    }
    
    @GetMapping("/browse.html")
    public String openPublicBrowserPage(Model model) {
        customSpringEventPublisher.publishCustomEvent("Anonymous entered public browse page!");
        model.addAttribute("publicItems", itemRepositoryMongo.findAll());
        return "browse";
    }
    
    //    void createItems() {
//        System.out.println("Data creation started...");
//        itemRepositoryMongo.save(new Item(1, "Agricola", Category.BOARDGAME, "Great game!", 40, 50.0));
//        itemRepositoryMongo.save(new Item(2, "7 Wonders", Category.BOARDGAME, "Complete novelty on the market!", 20, 60.0));
//        itemRepositoryMongo.save(new Item(3, "Ankh", Category.BOARDGAME, "Best old Egypt game out there!", 10, 100.0));
//        itemRepositoryMongo.save(new Item(4, "Dune: Imperium", Category.BOARDGAME, "Jason Momoa ftw!", 10, 50.0));
//        itemRepositoryMongo.save(new Item(5, "US Army starter pack", Category.WARGAMING, "It is just like the movies!", 5, 50.0));
//        itemRepositoryMongo.save(new Item(6, "Olive Drab Color", Category.DICE, "Best rolling experience ever!", 200, 14.99));
//        itemRepositoryMongo.save(new Item(7, "Space Marines x2", Category.MINI, "For the EMPEROR", 10, 159.99));
//        itemRepositoryMongo.save(new Item(8, "Calico", Category.BOARDGAME, "Cute and fuzzy!", 20, 59.99));
//        itemRepositoryMongo.save(new Item(9, "German Army starter pack", Category.WARGAMING, "Panzer to the rescue!", 10, 99.99));
//        itemRepositoryMongo.save(new Item(10, "Terra-forming Mars", Category.BOARDGAME, "Eat your heart out Musk!", 10, 50.00));
//        itemRepositoryMongo.save(new Item(11, "Witcher 3 Ciri dice", Category.DICE, "They glow in the dark!", 50, 14.99));
//        itemRepositoryMongo.save(new Item(12, "Witcher 3 Geralt", Category.MINI, "A beautiful collectible statuette!", 2, 299.99));
//        System.out.println("Data creation complete...");
//    }
    
//    void createUsers() {
//        System.out.println("Data creation started...");
//        Set<Role> r1 = new HashSet<>();
//        r1.add(new Role(1, RoleEnum.ROLE_USER));
//        User user = new User();
//        user.setId(1);
//        user.setUsername("user");
//        user.setPassword(bCryptPasswordEncoder().encode("password"));
//        user.setRoles(r1);
//        userRepositoryMongo.save(user);
//
//        r1.add(new Role(2, RoleEnum.ROLE_ADMIN));
//        User admin = new User();
//        admin.setId(2);
//        admin.setUsername("admin");
//        admin.setPassword(bCryptPasswordEncoder().encode("password"));
//        admin.setRoles(r1);
//        userRepositoryMongo.save(new User(2, "admin", bCryptPasswordEncoder().encode("password"), r1));
//        System.out.println("Data creation complete...");
//    }
    
//    void createRoles() {
//        System.out.println("Data creation started...");
//        Role r1 = new Role(1, RoleEnum.ROLE_USER);
//        roleRepositoryMongo.save(r1);
//        Role r2 = new Role(2, RoleEnum.ROLE_ADMIN);
//        roleRepositoryMongo.save(r2);
//        System.out.println("Data creation complete...");
//    }
    
//    public PasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    
}
