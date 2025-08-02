CREATE TABLE IF NOT EXISTS evento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(1000) NOT NULL,
    data_evento TIMESTAMP NOT NULL,
    local VARCHAR(200) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO evento (titulo, descricao, data_evento, local, ativo) VALUES
('Workshop Spring Boot', 'Workshop prático de Spring Boot para iniciantes e intermediários.', '2025-09-01 10:00:00', 'Auditório Central', TRUE),
('Palestra Segurança da Informação', 'Debate sobre tópicos avançados de segurança da informação e proteção de dados.', '2025-09-05 14:00:00', 'Sala 101', TRUE),
('Encontro de Desenvolvedores', 'Momento de networking para desenvolvedores de diversas áreas.', '2025-09-10 18:30:00', 'Espaço Tech', TRUE),
('Curso Angular Avançado', 'Curso intensivo abordando recursos avançados do Angular.', '2025-09-15 09:00:00', 'Laboratório 3', TRUE),
('Seminário de IA', 'Discussões aprofundadas sobre inteligência artificial e aprendizado de máquina.', '2025-09-20 16:00:00', 'Sala 202', TRUE),
('Treinamento DevOps', 'Capacitação em ferramentas, pipelines e práticas DevOps.', '2025-09-25 13:00:00', 'Sala 303', TRUE),
('Oficina Docker', 'Aplicação prática de containers Docker no dia a dia.', '2025-09-30 11:00:00', 'Sala 404', TRUE),
('Meetup de Microservices', 'Troca de experiências e boas práticas com microsserviços.', '2025-10-03 19:00:00', 'Espaço Inovação', TRUE),
('Workshop Kubernetes', 'Gerenciamento de clusters e aplicações no Kubernetes.', '2025-10-08 10:00:00', 'Laboratório 2', TRUE),
('Palestra Cloud Computing', 'Conceitos e arquiteturas em nuvem para empresas.', '2025-10-12 15:00:00', 'Sala Magna', TRUE);