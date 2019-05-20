package aplicacao;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dominio.Fornecedor;

public class Programa {
	public static void main(String[] args) {
		Fornecedor fornecedor = new Fornecedor();

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("prova-jpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Scanner acesso = new Scanner(System.in);
		int preferencia;

		while (true) {

			System.out.println("Digite 1: para listar fornecedores\n" + "Digite 2: Para buscar fornecedores pelo ID\n"
					+ "Digite 3: Para cadastrar um fornecedor\n" + "Digite 4: Para atualizar um fornecedor\n"
					+ "Digite 5: Para remover uma fornecedor\n" + "Digite 0: Para sair");

//--------------------------------------------------------------------------------------------------------------------------

			preferencia = pedirInteiro(acesso, "Digite uma das opcões acima: ");

			switch (preferencia) {

			case 1:
				String jpql = "SELECT f FROM Fornecedor f";
				List<Fornecedor> fornecedores = entityManager.createQuery(jpql, Fornecedor.class).getResultList();

				for (int i = 0; i < fornecedores.size(); i++) {
					System.out.println("fornecedores " + (i + 1) + ":");
					System.out.println(fornecedores.get(i) + "\n");
				}
				System.out.println();
				break;
//--------------------------------------------------------------------------------------------------------------------------

			case 2:
				int id = pedirInteiro(acesso, "Digite o id: ");

				Fornecedor fornecedorFound = entityManager.find(Fornecedor.class, id);
				if (fornecedorFound == null) {
					System.out.println("Não existe o o fornecedor com o ID digitado.");
				} else {
					System.out.println(fornecedorFound);
				}

				break;
//--------------------------------------------------------------------------------------------------------------------------

			case 3:
				System.out.println("Digite o nome: ");
				String nome = acesso.nextLine();
				System.out.println("Digite o cnpj: ");
				String cnpj = acesso.nextLine();
				Fornecedor fornecedor1 = new Fornecedor(null, nome, cnpj);
				entityManager.getTransaction().begin();
				entityManager.persist(fornecedor1);
				entityManager.getTransaction().commit();
				break;

//--------------------------------------------------------------------------------------------------------------------------

			case 4:
				int id1 = pedirInteiro(acesso, "Digite o ID: ");
				Fornecedor fornecedorFound1 = entityManager.find(Fornecedor.class, id1);

				if (fornecedorFound1 != null) {
					System.out.println("Digite o nome atual: ");
					String nome1 = acesso.nextLine();
					System.out.println("Digite o email atual: ");
					String cnpj1 = acesso.nextLine();
					fornecedorFound1.setCnpj(cnpj1);
					fornecedorFound1.setNome(nome1);
					entityManager.getTransaction().begin();
					entityManager.persist(fornecedorFound1);
					entityManager.getTransaction().commit();

				} else {
					System.out.println("Não é possível a atualização pois o id digitado não existe no Banco de Dados.");
				}
				break;

//--------------------------------------------------------------------------------------------------------------------------

			case 5:
				int id2 = pedirInteiro(acesso, "Digite o ID que almeja remover: ");
				Fornecedor fornecedorFound2 = entityManager.find(Fornecedor.class, id2);
				if (fornecedorFound2 == null) {
					System.out.println(
							"Não é possível remover o id solicitado, haja vista sua inexistência no Banco de Dados.");
				} else {
					System.out.println(fornecedorFound2);
				}
				entityManager.getTransaction().begin();
				entityManager.remove(fornecedorFound2);
				entityManager.getTransaction().commit();
				break;

//--------------------------------------------------------------------------------------------------------------------------

			case 0:
				System.out.println("Fechando o programa...Tchau!");
				entityManager.close();
				entityManagerFactory.close();
				System.exit(0);
				break;

			default:
				System.out.println("Verifique se o número que solicitou coincide com as opções acessíveis.");
				break;
			}

		}

	}

	public static int pedirInteiro(Scanner acesso, String mensagem) {
		int valor;
		System.out.println(mensagem);

		while (true) {
			try {
				valor = Integer.parseInt(acesso.nextLine());
				break;
			} catch (NumberFormatException e) {
				System.out.println("Tente outra vez!");
			}
		}

		return valor;
	}

}
