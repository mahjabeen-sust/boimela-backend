package com.rest_api.fs14backend.book;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Data
public class BookDTO {
	private UUID categoryId;
	private List<UUID> authorIdList;
	private long ISBN;
	private String title;
	private LocalDate publishedDate;
	private String description;
	private Book.Status status;
	private String publishers;
}
