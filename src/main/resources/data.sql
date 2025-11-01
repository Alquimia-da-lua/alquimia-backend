 -- DADOS INICIAIS (DML) - Inserção de um usuário ADMIN

  INSERT INTO public.usuario (
      cd_usuario,
      email_usuario,
      is_ativo,
      nm_usuario,
      nu_cpf,
      nu_telefone,
      role_usuario,
      senha_usuario
  )
  VALUES (
      1,
      'teste@teste.com',
      TRUE,
      'João Silva',
      '62535430401',
      '48999362138',
      'FUNCIONARIO',
      '$2a$10$DOzrhsOOiwWUsI0rf4g4Gu76VFJkk3cY9o/KFzgtoVHfTnITBWbGW'
  )
  ON CONFLICT (cd_usuario) DO NOTHING;
 SELECT setval('usuario_cd_usuario_seq', (SELECT MAX(cd_usuario) FROM usuario));

  -- DADOS INICIAIS (DML) - INSERÇÃO DE PRODUTOS

  -- Limpeza da tabela antes de inserir, se desejar recarregar os dados a cada inicialização:
  -- DELETE FROM public.produto;
  -- ALTER SEQUENCE produto_cd_produto_seq RESTART WITH 1; -- Reinicia o ID serial (opcional)

  INSERT INTO public.produto (
      cd_produto,
      nm_produto,
      ds_produto,
      vl_produto,
      categoria,
      cd_usuario,
      imagem
  ) VALUES
  (1,'Creme Corporal Natural de Abacaxi 200ml', 'Mergulhe no frescor do nosso Creme de Abacaxi, um elixir tropical de rápida absorção que hidrata profundamente, deixando sua pele incrivelmente macia, radiante e com um perfume deliciosamente vibrante. Sinta o toque de seda da natureza em 200ml de puro cuidado.', 199.90, 'PELE', 1, 'https://i.ibb.co/GfhcRh67/creme-corporal-abacaxi.png'),
  (2,'Creme Corporal Natural de Lavanda 200ml', 'Permita-se um ritual de calma com o nosso Creme de Lavanda, formulado para nutrir e acalmar. Sua textura suave e de rápida absorção hidrata intensamente, enquanto o aroma relaxante proporciona bem-estar e prepara sua pele para uma noite de descanso.', 199.90, 'PELE', 1, 'https://i.ibb.co/5hNZqjxx/creme-corporal-lavanda.png'),
  (3,'Creme Corporal Natural de Morango 200ml', 'Delicie sua pele com o nosso Creme de Morango, que combina hidratação profunda e um perfume doce e irresistível. Sua fórmula natural é rapidamente absorvida, deixando a pele macia, nutrida e com um brilho saudável e apetitoso.', 199.90, 'PELE', 1, 'https://i.ibb.co/NXD40jp/creme-corporal-morango.png'),
  (4,'Esfoliante Corporal Natural de Café 200 ml', 'Renove sua pele com o poder estimulante do café: este esfoliante remove as células mortas, melhora a circulação e deixa a pele incrivelmente lisa e aveludada. Desfrute da fragrância energizante e de uma pele visivelmente mais firme e radiante.', 99.90, 'PELE', 1, 'https://i.ibb.co/BYnLFQv/esfoliante-cafe.png'),
  (5,'Óleo Corporal Natural de Amêndoas Doces', 'Nosso óleo puro é o segredo para uma pele incrivelmente macia e elástica, prevenindo o ressecamento e o surgimento de estrias. Rico em vitaminas, ele nutre profundamente e proporciona um brilho natural, sendo ideal para massagens e hidratação diária.', 45.90, 'PELE', 1, 'https://i.ibb.co/Lhb59035/oleo-corporal-amendoas.png'),
  (6,'Sabonete Natural de Baunilha 90g', 'Transforme seu banho em um abraço com nosso Sabonete de Baunilha: ele limpa suavemente, cria uma espuma cremosa e deixa sua pele naturalmente macia e hidratada. Desfrute do aroma doce e acolhedor que proporciona conforto e bem-estar duradouros.', 45.90, 'PELE', 1, 'https://i.ibb.co/qMvnBSn1/sabonete-baunilha.png'),
  (7,'Sabonete Natural de Erva Doce 90g', 'Desfrute de uma limpeza suave e revitalizante com o frescor natural da Erva Doce. Este sabonete acalma a pele, controla a oleosidade e deixa uma fragrância delicada. Ideal para transformar o seu banho em um momento de puro bem-estar e equilíbrio.', 45.90, 'PELE', 1, 'https://i.ibb.co/qMyr7mb6/sabonete-erva-doce.png'),
  (8,'Sabonete Natural de Lavanda 90g', 'Desfrute de uma limpeza suave e revitalizante com o frescor natural da Erva Doce. Este sabonete acalma a pele, controla a oleosidade e deixa uma fragrância delicada. Ideal para transformar o seu banho em um momento de puro bem-estar e equilíbrio.', 45.90, 'PELE', 1, 'https://i.ibb.co/hJ8ht57C/sabonete-lavanda.png'),
  (9,'Sabonete Natural Líquido de Cacau 135ml', 'Um ritual de limpeza luxuoso que nutre e revitaliza a pele com o poder antioxidante do cacau. Sua fórmula líquida delicada e envolvente promove maciez e melhora a elasticidade, deixando a pele com um toque aveludado e irresistível.', 64.90, 'PELE', 1, 'https://i.ibb.co/cK22tTbB/sabonete-liquido-cacau.png'),
  (10,'Shampoo Natural de Maracujá 250ml', 'Limpeza purificante e leveza natural para os seus cabelos. Formulado com extrato de Maracujá, ele equilibra a oleosidade do couro cabeludo, fortalece os fios e oferece um frescor suave e revigorante.', 69.90, 'CABELO', 1, 'https://i.ibb.co/FkkknG2Y/shampoo-maracuja.png'),
  (11,'Condicionador Natural de Maracujá 250ml', 'O toque final de nutrição e frescor. Rico em vitaminas, sela as cutículas, desembaraça e confere brilho intenso, mantendo os fios leves, macios e protegidos contra o ressecamento.', 69.90, 'CABELO', 1, 'https://i.ibb.co/zhkVzr4t/condicionador-maracuja.png'),
  (12,'Creme Capilar de Aloe Vera 200ml', 'Poderoso fortalecimento e hidratação profunda sem pesar. Com puro gel de Aloe Vera, sela as pontas, controla o frizz e protege os fios do calor, deixando-os macios, leves e com um brilho incrível.', 99.90, 'CABELO', 1, 'https://i.ibb.co/JjNFHXZj/creme-capilar-aloevera.png'),
  (13,'Shampoo Natural de Romã 250ml', 'Limpa suavemente enquanto protege e realça a cor. Rico em antioxidantes da Romã, fortalece o fio, previne o desbotamento e confere um brilho vibrante e duradouro.', 69.90, 'CABELO', 1, 'https://i.ibb.co/q3xkXZWc/shampoo-roma.png'),
  (14,'Condicionador Natural de Romã 250ml', 'Fórmula antioxidante que sela as cutículas, desembaraça instantaneamente e fixa a cor nos fios. Garante hidratação, maciez e um brilho intenso que realça a vitalidade da sua coloração.', 69.90, 'CABELO', 1, 'https://i.ibb.co/hFh46pXy/condicionador-roma.png'),
  (15,'Creme Capilar de Pêssego 200ml', 'Nutrição e hidratação com extrato de pêssego, rico em vitaminas e minerais. Recupera o ressecamento, sela os fios para reduzir o frizz e entrega um brilho luminoso e sedoso, com uma fragrância alegre.', 99.90, 'CABELO', 1, 'https://i.ibb.co/HDYZSF7H/creme-capilar-pessego.png'),
  (16,'Shampoo Natural de Amêndoas 250ml', 'Fórmula suave, enriquecida com Óleo de Amêndoas, que limpa delicadamente sem ressecar. Restaura a nutrição natural dos fios, fortalece e confere brilho e maciez aveludada.', 69.90, 'CABELO', 1, 'https://i.ibb.co/NnZtBVBQ/shampoo-amendoas.png'),
  (17,'Condicionador Natural de Amêndoas 250ml', 'Fórmula altamente nutritiva com Óleo de Amêndoas para selar as cutículas e reter a hidratação. Desembaraça instantaneamente, combate o ressecamento e entrega maciez e brilho intenso.', 69.90, 'CABELO', 1, 'https://i.ibb.co/8n5sCw0Z/condicionador-amendoas.png'),
  (18,'Creme Capilar Natural de Calêndula 200ml', 'Creme com Extrato de Calêndula, conhecido por suas propriedades calmantes e regeneradoras. Hidrata profundamente, suaviza o couro cabeludo sensível, elimina o frizz e realça o brilho natural dos fios.', 99.90, 'CABELO', 1, 'https://i.ibb.co/W43y4rJK/creme-capilar-calendula.png'),
  (19,'Óleo Essencial Natural de Tangerina 30ml', 'O aroma cítrico, doce e revigorante da Tangerina (Mandarin). Promove um ambiente de alegria, leveza e bem-estar, sendo ideal para aliviar o estresse, a ansiedade e elevar o humor.', 49.90, 'AROMATIZANTE', 1, 'https://i.ibb.co/VW7B7tW5/aromatizante-oleo-tangerina.png'),
  (20,'Óleo Essencial Natural de Alecrim 30ml', 'Com um aroma herbal, fresco e revigorante. É o óleo da concentração e do foco, ideal para estudos e trabalho, pois estimula a memória, combate a fadiga mental e proporciona energia.', 49.90, 'AROMATIZANTE', 1, 'https://i.ibb.co/3yfYV9wN/aromatizante-oleo-alecrim.png'),
  (21,'Aromatizante em Spray Natural de Maracujá 50ml', 'Fragrância tropical, frutal e levemente cítrica que instantaneamente acalma e relaxa. Promove uma atmosfera de tranquilidade, bem-estar e alívio do estresse e da ansiedade.', 79.90, 'AROMATIZANTE', 1, 'https://i.ibb.co/PzGn1Fk9/aromatizante-spray-maracuja.png'),
  (22,'Difusor de Ambiente Natural de Hortelã 270ml', 'Com um aroma fresco, mentolado e revigorante. Excelente para estimular a concentração, clarear a mente e afastar a fadiga mental. Cria uma atmosfera de foco, energia e frescor, auxiliando também na sensação de alívio respiratório.', 149.90, 'AROMATIZANTE', 1, 'https://i.ibb.co/Gv7HgV99/aromatizante-hortela.png'),
  (23,'Difusor de Ambiente Natural de Rosa Branca 270ml', 'Fragrância floral, suave e elegantemente adocicada. Promove uma profunda sensação de paz interior, pureza e harmonia. Ideal para criar um ambiente de serenidade, equilibrar emoções e aliviar o estresse, sendo excelente para meditação ou descanso.', 149.90, 'AROMATIZANTE', 1, 'https://i.ibb.co/8whtBM5/aromatizante-rosa-branca.png'),
  (24,'Aromatizante em Spray Natural de Limão 50ml', 'Aroma cítrico, vibrante e intensamente refrescante. Ideal para promover clareza mental, foco e elevar o humor, combatendo a fadiga e a letargia. Proporciona uma sensação imediata de limpeza e energia, sendo perfeito para cozinhas, escritórios e banheiros.', 79.90, 'AROMATIZANTE', 1, 'https://i.ibb.co/TDgq98fd/aromatizante-spray-limao.png')
  ON CONFLICT (cd_produto) DO NOTHING;
 SELECT setval('produto_cd_produto_seq', (SELECT MAX(cd_produto) FROM produto));

-- DADOS INICIAIS (DML) - INSERÇÃO DE ESTOQUE

 INSERT INTO public.estoque (
     cd_estoque,
     is_ativo
 )
 VALUES (
            1,
            TRUE
        )
     ON CONFLICT (cd_estoque) DO NOTHING;

 SELECT setval('estoque_cd_estoque_seq', (SELECT MAX(cd_estoque) FROM estoque));

 -- DADOS INICIAIS (DML) - INSERÇÃO DE ITENS DE ESTOQUE
-- Cada produto com quantidade inicial de 200

 INSERT INTO public.item_estoque (
     cd_item_estoque,
     cd_produto,
     cd_estoque,
     qt_item_estoque
 )
 VALUES
     (1, 1, 1, 200),
     (2, 2, 1, 200),
     (3, 3, 1, 200),
     (4, 4, 1, 200),
     (5, 5, 1, 200),
     (6, 6, 1, 200),
     (7, 7, 1, 200),
     (8, 8, 1, 200),
     (9, 9, 1, 200),
     (10, 10, 1, 200),
     (11, 11, 1, 200),
     (12, 12, 1, 200),
     (13, 13, 1, 200),
     (14, 14, 1, 200),
     (15, 15, 1, 200),
     (16, 16, 1, 200),
     (17, 17, 1, 200),
     (18, 18, 1, 200),
     (19, 19, 1, 200),
     (20, 20, 1, 200),
     (21, 21, 1, 200),
     (22, 22, 1, 200),
     (23, 23, 1, 200),
     (24, 24, 1, 200)
     ON CONFLICT (cd_item_estoque) DO NOTHING;

 SELECT setval('item_estoque_cd_item_estoque_seq', (SELECT MAX(cd_item_estoque) FROM item_estoque));
