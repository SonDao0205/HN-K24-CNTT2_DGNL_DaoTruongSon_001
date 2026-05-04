package com.dgnl.artwork.repository;

import com.dgnl.artwork.model.Artwork;
import com.dgnl.artwork.model.Category;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final ArtworkRepository artworkRepository;

    public InitData(CategoryRepository categoryRepository, ArtworkRepository artworkRepository) {
        this.categoryRepository = categoryRepository;
        this.artworkRepository = artworkRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0 && artworkRepository.count() == 0) {
            List<Category> categories = new ArrayList<>();
            categories.add(new Category("Hội họa Thế giới", "Các tác phẩm hội họa kinh điển và hiện đại"));
            categories.add(new Category("Điêu khắc Nghệ thuật", "Các tác phẩm điêu khắc từ đá, đồng và gỗ"));
            categories.add(new Category("Nhiếp ảnh Kỹ thuật số", "Nghệ thuật bắt trọn khoảnh khắc qua ống kính"));

            List<Category> savedCategories = categoryRepository.saveAll(categories);

            List<Artwork> artworks = new ArrayList<>();

            for (Category cat : savedCategories) {
                String catName = cat.getName();

                for (int i = 1; i <= 4; i++) {
                    Artwork art = new Artwork();
                    art.setTitle("Tác phẩm " + catName + " số " + i);
                    art.setArtist("Nghệ nhân " + i);
                    art.setPrice(100.0 * i + 50);
                    art.setReleaseDate(LocalDate.now().minusMonths(i * 2));
                    art.setCoverImage("image_" + cat.getId() + "_" + i + ".jpg");
                    art.setCategory(cat);
                    art.setStatus(true);

                    artworks.add(art);
                }
            }

            artworkRepository.saveAll(artworks);
        }
    }
}
