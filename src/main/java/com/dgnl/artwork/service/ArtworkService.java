package com.dgnl.artwork.service;

import com.dgnl.artwork.model.Artwork;
import com.dgnl.artwork.model.Category;
import com.dgnl.artwork.repository.ArtworkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtworkService {
    private final ArtworkRepository artworkRepository;

    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public Artwork getArtworkById(Long artworkId) {
        return artworkRepository.findById(artworkId).orElse(null);
    }

    public Page<Artwork> findPaginated(int pageNo, int pageSize, String title, Long categoryId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        if (title != null && !title.isEmpty() && categoryId != null) {
            return artworkRepository.findByTitleContainingIgnoreCaseAndCategoryId(title, categoryId, pageable);
        } else if (title != null && !title.isEmpty()) {
            return artworkRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (categoryId != null) {
            return artworkRepository.findByCategoryId(categoryId, pageable);
        }

        return artworkRepository.findAll(pageable);
    }

    public boolean save(Artwork artwork) {
        if (artwork != null) {
            artworkRepository.save(artwork);
            return true;
        }
        return false;
    }

    public void deleteArtwork(Long artworkId) {
        artworkRepository.deleteById(artworkId);
    }
}
