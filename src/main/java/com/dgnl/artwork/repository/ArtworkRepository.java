package com.dgnl.artwork.repository;

import com.dgnl.artwork.model.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    Page<Artwork> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Artwork> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Artwork> findByTitleContainingIgnoreCaseAndCategoryId(String title, Long categoryId, Pageable pageable);
}
