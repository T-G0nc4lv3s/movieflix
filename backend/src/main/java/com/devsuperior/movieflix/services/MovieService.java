package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;


@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Transactional(readOnly = true)
	public MovieDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie movie = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		GenreDTO dto = new GenreDTO(movie.getGenre());
		return new MovieDTO(movie, dto);
	}
	
	@Transactional(readOnly = true)
	public Page<MovieDTO> findByGenre(Long genreId, Pageable pageable) {
		genreId = (genreId == 0) ? null : genreId;
		Page<Movie> page = repository.findByGenreId(genreId, pageable);
		return page.map(x -> new MovieDTO(x, new GenreDTO(x.getGenre())));
	}
	
}
