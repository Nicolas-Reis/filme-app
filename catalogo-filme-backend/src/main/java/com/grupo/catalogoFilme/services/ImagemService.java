package com.grupo.catalogoFilme.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;

@Service
public class ImagemService {

	private final Cloudinary cloudinary;

	public ImagemService(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	public String upload(MultipartFile arquivo, String pasta) {
		if (arquivo == null || arquivo.isEmpty())
			throw new DadosInvalidosException("Arquivo de imagem é obrigatório.");
		String tipo = arquivo.getContentType();
		if (tipo == null || !tipo.startsWith("image/"))
			throw new DadosInvalidosException("O arquivo deve ser uma imagem.");
		try {
			Map<?, ?> resultado = cloudinary.uploader().upload(arquivo.getBytes(),
					ObjectUtils.asMap("folder", pasta, "resource_type", "image"));
			return resultado.get("secure_url").toString();
		} catch (IOException e) {
			throw new DadosInvalidosException("Falha ao enviar a imagem.");
		}
	}

	public record ImagemBinaria(byte[] conteudo, MediaType contentType) {}

	public ImagemBinaria baixar(String url) {
		if (url == null || url.isBlank())
			throw new RegistroNaoEncontradoException("Imagem não encontrada.");
		try {
			HttpResponse<byte[]> resp = HttpClient.newHttpClient().send(
					HttpRequest.newBuilder(URI.create(url)).GET().build(),
					HttpResponse.BodyHandlers.ofByteArray());
			MediaType tipo = MediaType.parseMediaType(
					resp.headers().firstValue("Content-Type").orElse("application/octet-stream"));
			return new ImagemBinaria(resp.body(), tipo);
		} catch (IOException | InterruptedException e) {
			if (e instanceof InterruptedException) Thread.currentThread().interrupt();
			throw new DadosInvalidosException("Falha ao obter a imagem.");
		}
	}
}
