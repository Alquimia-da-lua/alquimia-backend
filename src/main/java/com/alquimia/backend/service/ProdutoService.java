package com.alquimia.backend.service;

import com.alquimia.backend.dto.request.ProdutoRequestDTO;
import com.alquimia.backend.dto.response.ProdutoResponseDTO;
import com.alquimia.backend.exception.ProdutoNaoEncontradoException;
import com.alquimia.backend.model.Produto;
import com.alquimia.backend.model.Usuario;
import com.alquimia.backend.repository.ProdutoRepository;
import com.alquimia.backend.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;

    @Transactional
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO produtoDto) {
        var produto = new Produto();
        BeanUtils.copyProperties(produtoDto, produto);
        return new ProdutoResponseDTO(produtoRepository.save(produto));
    }

    @Transactional
    public List<ProdutoResponseDTO> listarProdutos() {
        List<ProdutoResponseDTO> produtos = new ArrayList<>();

        List<Produto> model = this.produtoRepository.findAll();
        for (Produto produto : model) {
            produtos.add(new ProdutoResponseDTO(produto));
        }
        return produtos;
    }

    @Transactional
    public ProdutoResponseDTO buscarProduto(Integer cdProduto) {
        return this.produtoRepository.findByCdProduto(cdProduto)
                .map(ProdutoResponseDTO::new)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(cdProduto));
    }

    @Transactional
    public List<ProdutoResponseDTO> listarProdutosAtivos() {
        List<ProdutoResponseDTO> produtos = new ArrayList<>();

        List<Produto> model = this.produtoRepository.findAllByIsAtivoTrue().stream().toList();
        for (Produto produto : model) {
            produtos.add(new ProdutoResponseDTO(produto));
        }
        return produtos;
    }

    @Transactional
    public ProdutoResponseDTO alterarProduto(Integer cdProduto, ProdutoRequestDTO produtoDto) {
        Produto produtoParaAtualizar = this.produtoRepository.findByCdProduto(cdProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(cdProduto));

        BeanUtils.copyProperties(produtoDto, produtoParaAtualizar);
        return new ProdutoResponseDTO(produtoRepository.save(produtoParaAtualizar));

    }

    @Transactional
    public void deletarProduto(Integer cdProduto) {
        Produto produtoParaDeletar = this.produtoRepository.findByCdProduto(cdProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(cdProduto));
        produtoParaDeletar.marcarComoDeletado();
    }


}
