package br.com.zup.mercadolivre.adicionaimagem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Primary
public class Uploader  {

	public Set<String> envia(List<MultipartFile> imagens) {
		
		return imagens.stream()
				.map(imagem -> "C:\\Users\\Zupper\\Pictures\\Saved Pictures"
						+ imagem.getOriginalFilename())
				.collect(Collectors.toSet());
	}
}
