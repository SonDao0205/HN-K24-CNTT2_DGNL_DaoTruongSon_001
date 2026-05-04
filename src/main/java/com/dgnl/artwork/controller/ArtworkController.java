package com.dgnl.artwork.controller;

import com.dgnl.artwork.model.Artwork;
import com.dgnl.artwork.model.Category;
import com.dgnl.artwork.service.ArtworkService;
import com.dgnl.artwork.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/artwork")
public class ArtworkController {
    private final ArtworkService artworkService;
    private final CategoryService categoryService;
    public ArtworkController(ArtworkService artworkService, CategoryService categoryService) {
        this.artworkService = artworkService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listProduct(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(required = false) String title,
                              @RequestParam(required = false) Long categoryId,
                              Model model) {

        Page<Artwork> artworks = artworkService.findPaginated(page, 5, title, categoryId);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", artworks.getTotalPages());
        model.addAttribute("totalItems", artworks.getTotalElements());
        model.addAttribute("artworks", artworks.getContent());

        model.addAttribute("title", title);
        model.addAttribute("categoryId", categoryId);

        model.addAttribute("categories", categoryService.getAllCategories());

        return "artworkList";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("artwork", new Artwork());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "artworkAdd";
    }


    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        model.addAttribute("artwork",artworkService.getArtworkById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "artworkAdd";
    }


    @GetMapping("/delete/{id}")
    public String deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
        return "redirect:/artwork/list";
    }

    @PostMapping("/save")
    public String saveArtwork(@Valid @ModelAttribute("artwork") Artwork artwork,
                              BindingResult bindingResult,
                              @RequestParam("file") MultipartFile file,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "artworkAdd";
        }

        try {
            if (!file.isEmpty()) {
                System.out.println("sơn ok");
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String uploadDir = "uploads/";

                java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
                System.out.println("uploadPath: " + uploadPath);
                if (!java.nio.file.Files.exists(uploadPath)) {
                    java.nio.file.Files.createDirectories(uploadPath);
                }

                java.nio.file.Path filePath = uploadPath.resolve(fileName);
                java.nio.file.Files.copy(file.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                artwork.setCoverImage(fileName);
            } else if (artwork.getId() != null) {
                System.out.println("sơn ko");
                Artwork oldArtwork = artworkService.getArtworkById(artwork.getId());
                artwork.setCoverImage(oldArtwork.getCoverImage());
            }

            artworkService.save(artwork);
        } catch (Exception e) {
            System.out.println("sơn error");
            e.printStackTrace();
        }

        return "redirect:/artwork/list";
    }


}
