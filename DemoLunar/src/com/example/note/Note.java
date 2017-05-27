package com.example.note;

import java.io.Serializable;

public class Note implements Serializable {
	public String nameNote;
	public String detailNote;
	public String imageNote;
	public String date;
	public long id;

	public Note(String nameNote, String detailNote, String imageNote, String date,long id) {
		super();
		this.nameNote = nameNote;
		this.detailNote = detailNote;
		this.imageNote = imageNote;
		this.date = date;
		this.id=id;
	}

	public Note(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNameNote() {
		return nameNote;
	}

	public void setNameNote(String nameNote) {
		this.nameNote = nameNote;
	}

	public String getDetailNote() {
		return detailNote;
	}

	public void setDetailNote(String detailNote) {
		this.detailNote = detailNote;
	}

	public String getImageNote() {
		return imageNote;
	}

	public void setImageNote(String imageNote) {
		this.imageNote = imageNote;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
