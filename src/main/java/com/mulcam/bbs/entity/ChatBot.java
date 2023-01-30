package com.mulcam.bbs.entity;

public class ChatBot {
	private String category;
	private String user;
	private String chatbot;
	private Double similarity;
	
	public ChatBot() { }
	public ChatBot(String category, String user, String chatbot, Double similarity) {
		this.category = category;
		this.user = user;
		this.chatbot = chatbot;
		this.similarity = similarity;
	}
	
	@Override
	public String toString() {
		return "ChatBot [category=" + category + ", user=" + user + ", chatbot=" + chatbot + ", similarity="
				+ similarity + "]";
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getChatbot() {
		return chatbot;
	}
	public void setChatbot(String chatbot) {
		this.chatbot = chatbot;
	}
	public Double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}
}
