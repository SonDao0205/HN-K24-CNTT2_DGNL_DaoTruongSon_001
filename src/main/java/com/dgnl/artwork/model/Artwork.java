package com.dgnl.artwork.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "artworks")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 5, max = 150,message = "Độ dài tên không hợp lệ!")
    private String title;
    @NotBlank(message = "Tác giả không được để trống!")
    private String artist;
    private Double price;
    @PastOrPresent(message = "Ngày phát hành không được là ngày trong tương lai")
    private LocalDate releaseDate;
    private String coverImage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    private Boolean status;
}
