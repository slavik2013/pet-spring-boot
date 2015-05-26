package ua.in.petybay.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories({"ua.in.petybay.dao"})
@ComponentScan({"ua.in.petybay.controller", "ua.in.petybay", "ua.in.petybay.configuration", "ua.in.petybay.service.image.picasa"})
@EnableAspectJAutoProxy
public class Application {
//public class Application implements CommandLineRunner {
//
//	@Autowired
//	private CustomerRepository repository;
//
//	@Autowired private MongoOperations mongoOperations;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		repository.deleteAll();
//
//		MyCar myCar = new MyCar("green","audi");
//
//		Customer customer1 = repository.save(new Customer("Bob", "2", myCar));
//
////		mongoOperations.save(new Customer("Bob", "2", myCar));
//
//		System.out.println("-------------------------------");
//		System.out.println("customer1.getId() = " + customer1.getId());
//		System.out.println("Customers found with findAll():");
//		System.out.println("-------------------------------");
//		for (Customer customer : repository.findAll()) {
//			System.out.println(customer);
//		}
//
//	}

//	@Autowired
//	private PetRepository petRepository;
//
//	@Autowired
//	private CategoryRepository categoryRepository;
//
//	@Autowired
//	private BreedRepository breedRepository;
//
//	@Autowired
//	private OwnerRepository ownerRepository;
//
//	@Override
//	public void run(String... args) throws Exception {
//		List<String> list = new ArrayList<String>(){
//			{
//				add("cat1.jpg");
//			}
//		};
////		Category category = new Category("cat","cat.jpg");
////		category = categoryRepository.save(category);
////		Breed breed = new Breed(category, "persid", "persid.jpg");
////		breed = breedRepository.save(breed);
////		Owner owner = new Owner("mail@i.ua", "John","12345", "johnskype", "pass", new Date(2015, 3, 30));
////		owner = ownerRepository.save(owner);
////		Pet pet = new Pet(category, "white", new Date(2014, 10, 10), "male","no","no",
////				"kyiv",100,breed,owner,list, new Date(),"some text about pet","best cat", 20, true);
////
////		petRepository.deleteAll();
////		petRepository.save(pet);
////
////		System.out.println("-------------------------------");
////		for (Pet pet1 : petRepository.findAll()){
////			System.out.println("pet1 = " + pet1);
////		}
//	}

}
