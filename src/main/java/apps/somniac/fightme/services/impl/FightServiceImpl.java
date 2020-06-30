package apps.somniac.fightme.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apps.somniac.fightme.converters.EntityToDto;
import apps.somniac.fightme.dtos.CharacterDto;
import apps.somniac.fightme.dtos.FightDto;
import apps.somniac.fightme.dtos.ManualFightDto;
import apps.somniac.fightme.dtos.SkillDto;
import apps.somniac.fightme.entities.CharacterEntity;
import apps.somniac.fightme.exceptions.CharacterNoContentException;
import apps.somniac.fightme.exceptions.messages.DataErrorMessages;
import apps.somniac.fightme.repositories.CharacterRepository;
import apps.somniac.fightme.services.FightService;

@Service
public class FightServiceImpl implements FightService {

	private Logger logger = LoggerFactory.getLogger(CharacterServiceImpl.class);
	
	public FightDto fight = new FightDto();
	public ManualFightDto mFight = new ManualFightDto();
	
	
	@Autowired
	public CharacterRepository characterRepository;

	@Autowired
	public EntityToDto etd;


	@Override
	public FightDto randomFight(List<CharacterDto> players) {

		CharacterEntity entityPlayer1 = higherSpeed(players.get(0).getIdCharacter(), players.get(1).getIdCharacter());
		CharacterEntity entityPlayer2 = lowerSpeed(players.get(0).getIdCharacter(), players.get(1).getIdCharacter());

		CharacterDto player1 = etd.getCharacter(entityPlayer1);
		CharacterDto player2 = etd.getCharacter(entityPlayer2);

		SkillDto player1Skill;
		SkillDto player2Skill;
		
		List<SkillDto> movesPlayer1 = new ArrayList<SkillDto>();
		List<SkillDto> movesPlayer2 = new ArrayList<SkillDto>();
		List<SkillDto> allMoves = new ArrayList<SkillDto>();
		
		Integer random1 = 0;
		Integer random2 = 0;
		Integer cont = 1;

		while(player1.getHp() > 0 || player2.getHp() > 0) {

			System.out.println();
			System.out.println("TURNO " + cont + " -- " + player1.getName() + ": " + player1.getHp() + " HP -- " + player2.getName() + ": " + player2.getHp() + " HP");
			System.out.println("------------------------------------------------------------------");

			random1 = (int) (5 * Math.random());
			player1Skill = player1.getCharacterRol().getSkills().get(random1);

			random2 = (int) (5 * Math.random());
			player2Skill = player2.getCharacterRol().getSkills().get(random2);

			movesPlayer1.add(player1Skill);
			movesPlayer2.add(player2Skill);
			
			allMoves.add(player1Skill);
			allMoves.add(player2Skill);
			
			fight.setMovesPlayer1(movesPlayer1);
			fight.setMovesPlayer2(movesPlayer2);
			fight.setAllMoves(allMoves);
			fight.setFaster(player1);
			
			// Si el jugador 1 usa una técnica de ATAQUE y ...
			if (player1Skill.getAction().toString().equals("ATAQUE")) {

				System.out.println(player1.getName() + " ha usado " + player1Skill.getName());


				// ... el jugador 2 usa una técnica de ATAQUE
				if(player2Skill.getAction().toString().equals("ATAQUE")) {

					System.out.println(player2.getName() + " ha perdido " + damage(player1Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() - damage(player1Skill.getQuantity(), player2.getHp()));

					if (isGameOver(player2.getHp())) {
						entityPlayer1.setVictories(entityPlayer1.getVictories() + 1);
						entityPlayer2.setDefeats(entityPlayer2.getDefeats() + 1);
						characterRepository.save(getExp(entityPlayer1));
						characterRepository.save(entityPlayer2);
						fight.setWinner(etd.getCharacter(entityPlayer1));
						
						System.out.println("ganador: " + fight.getWinner() + "movimientos1: " + fight.getMovesPlayer1() + "movimientos2: " + fight.getMovesPlayer2());
						return fight;
					}

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName());

					System.out.println(player1.getName() + " ha perdido " + damage(player2Skill.getQuantity(), player1.getHp()) + " puntos de vida");

					player1.setHp(player1.getHp() - damage(player2Skill.getQuantity(), player1.getHp()));

					if (isGameOver(player1.getHp())) {
						entityPlayer2.setVictories(entityPlayer2.getVictories() + 1);
						entityPlayer1.setDefeats(entityPlayer1.getDefeats() + 1);
						characterRepository.save(entityPlayer1);
						characterRepository.save(getExp(entityPlayer2));
						fight.setWinner(etd.getCharacter(entityPlayer2));
						
						System.out.println("ganador: " + fight.getWinner() + "movimientos1: " + fight.getMovesPlayer1() + "movimientos2: " + fight.getMovesPlayer2());
						return fight;
					}

				}



				// ... el jugador 2 usa una técnica de DEFENSA
				if(player2Skill.getAction().toString().equals("DEFENSA")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " para defenderse");

					System.out.println(player2.getName() + " ha perdido " + def(player1Skill.getQuantity(), player2Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() - def(player1Skill.getQuantity(), player2Skill.getQuantity(), player2.getHp()));

					if (isGameOver(player2.getHp())) {
						entityPlayer1.setVictories(entityPlayer1.getVictories() + 1);
						entityPlayer2.setDefeats(entityPlayer2.getDefeats() + 1);
						characterRepository.save(getExp(entityPlayer1));
						characterRepository.save(entityPlayer2);
						fight.setWinner(etd.getCharacter(entityPlayer1));
						
						System.out.println("ganador: " + fight.getWinner() + "movimientos1: " + fight.getMovesPlayer1() + "movimientos2: " + fight.getMovesPlayer2());
						
						return fight;
					}

				}



				// ... el jugador 2 usa una técnica de CURA
				if(player2Skill.getAction().toString().equals("CURA")) {

					System.out.println(player2.getName() + " ha perdido " + damage(player1Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() - damage(player1Skill.getQuantity(), player2.getHp()));

					if (isGameOver(player2.getHp())) {
						entityPlayer1.setVictories(entityPlayer1.getVictories() + 1);
						entityPlayer2.setDefeats(entityPlayer2.getDefeats() + 1);
						characterRepository.save(getExp(entityPlayer1));
						characterRepository.save(entityPlayer2);
						fight.setWinner(etd.getCharacter(entityPlayer1));
						
						System.out.println("ganador: " + fight.getWinner() + "movimientos1: " + fight.getMovesPlayer1() + "movimientos2: " + fight.getMovesPlayer2());
						return fight;
					}

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " y se ha curado " + heal(player2Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() + heal(player2Skill.getQuantity(), player2.getHp()));

				}

			}




			// Si el jugador 1 usa una técnica de DEFENSA y ...
			else if (player1Skill.getAction().toString().equals("DEFENSA")) {

				System.out.println(player1.getName() + " ha usado " + player1Skill.getName() + " para defenderse");


				// ... el jugador 2 usa una técnica de ATAQUE
				if(player2Skill.getAction().toString().equals("ATAQUE")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName());

					System.out.println(player1.getName() + " ha perdido " + def(player2Skill.getQuantity(), player1Skill.getQuantity(), player1.getHp()) + " puntos de vida");

					player1.setHp(player1.getHp() - def(player2Skill.getQuantity(), player1Skill.getQuantity(), player1.getHp()));

					if (isGameOver(player1.getHp())) {
						entityPlayer2.setVictories(entityPlayer2.getVictories() + 1);
						entityPlayer1.setDefeats(entityPlayer1.getDefeats() + 1);
						characterRepository.save(entityPlayer1);
						characterRepository.save(getExp(entityPlayer2));
						fight.setWinner(etd.getCharacter(entityPlayer2));
						
						System.out.println("ganador: " + fight.getWinner() + "movimientos1: " + fight.getMovesPlayer1() + "movimientos2: " + fight.getMovesPlayer2());
						return fight;
					}

				}



				// ... el jugador 2 usa una técnica de DEFENSA
				if(player2Skill.getAction().toString().equals("DEFENSA")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " para defenderse");

				}



				// ... el jugador 2 usa una técnica de CURA
				if(player2Skill.getAction().toString().equals("CURA")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " y se ha curado " + heal(player2Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() + heal(player2Skill.getQuantity(), player2.getHp()));

				}

			}




			// Si el jugador 1 usa una técnica de CURA y ...
			else if (player1Skill.getAction().toString().equals("CURA")) {

				System.out.println(player1.getName() + " ha usado " + player1Skill.getName() + " y se ha curado " + heal(player1Skill.getQuantity(), player1.getHp()) + " puntos de vida");

				player1.setHp(player1.getHp() + heal(player1Skill.getQuantity(), player1.getHp()));


				// ... el jugador 2 usa una técnica de ATAQUE
				if(player2Skill.getAction().toString().equals("ATAQUE")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName());

					System.out.println(player1.getName() + " ha perdido " + def(player2Skill.getQuantity(), player1Skill.getQuantity(), player1.getHp()) + " puntos de vida");

					player1.setHp(player1.getHp() - def(player2Skill.getQuantity(), player1Skill.getQuantity(), player1.getHp()));

					if (isGameOver(player1.getHp())) {
						entityPlayer2.setVictories(entityPlayer2.getVictories() + 1);
						entityPlayer1.setDefeats(entityPlayer1.getDefeats() + 1);
						characterRepository.save(entityPlayer1);
						characterRepository.save(getExp(entityPlayer2));
						fight.setWinner(etd.getCharacter(entityPlayer2));
						
						System.out.println("ganador: " + fight.getWinner() + "movimientos1: " + fight.getMovesPlayer1() + "movimientos2: " + fight.getMovesPlayer2());
						return fight;
					}

				}



				// ... el jugador 2 usa una técnica de DEFENSA
				if(player2Skill.getAction().toString().equals("DEFENSA")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " para defenderse");

				}



				// ... el jugador 2 usa una técnica de CURA
				if(player2Skill.getAction().toString().equals("CURA")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " y se ha curado " + heal(player2Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() + heal(player2Skill.getQuantity(), player2.getHp()));

				}

			}

			cont++;

		}
		return null;

	}
	
	
	
	// ---------- FUNCIONES DE COMBATE ------------------------------------------------------------------------------------------------------- //
	
	// Función privada para hacer daño directo
	private long damage(long dmg, long hp) {
		if (dmg >= hp)
			return hp;
		else
			return dmg;
	}
	
	// Función privada para hacer daño, estando el rival en posición defensiva
	private long def(long dmg, long df, long hp) {
		if (dmg > df) {
			if (dmg > hp)
				return hp;
			else
				return dmg - df;
		} else {
			if (dmg > hp)
				return hp;
			else
				return 0;
		}
	}
	
	// Función privada para restaurar HP
	private long heal(long h, long hp) {
		if (h + hp >= 100)
			return 100 - hp;
		else
			return h;
	}
	
	// Función privada para comprobar si algún personaje ha llegado a 0 HP
	private boolean isGameOver(long hp) {
		return hp <= 0;
	}
	
	
	
	// ---------- FUNCIONES DE JUEGO ------------------------------------------------------------------------------------------------------- //

	// Función privada para obtener, de dos personajes, el que sea más veloz
	private CharacterEntity higherSpeed(long idP1, long idP2) {

		CharacterEntity entity1 = characterRepository.findById(idP1).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CHARACTER_NO_CONTENT);
			throw new CharacterNoContentException(DataErrorMessages.CHARACTER_NO_CONTENT);
		});

		CharacterEntity entity2 = characterRepository.findById(idP2).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CHARACTER_NO_CONTENT);
			throw new CharacterNoContentException(DataErrorMessages.CHARACTER_NO_CONTENT);
		});

		if (entity1.getSpeed() >= entity2.getSpeed())
			return entity1;
		else
			return entity2;

	}
	
	// Función privada para obtener, de dos personajes, el que sea menos veloz
	private CharacterEntity lowerSpeed(long idP1, long idP2) {

		CharacterEntity entity1 = characterRepository.findById(idP1).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CHARACTER_NO_CONTENT);
			throw new CharacterNoContentException(DataErrorMessages.CHARACTER_NO_CONTENT);
		});

		CharacterEntity entity2 = characterRepository.findById(idP2).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CHARACTER_NO_CONTENT);
			throw new CharacterNoContentException(DataErrorMessages.CHARACTER_NO_CONTENT);
		});

		if (entity1.getSpeed() <= entity2.getSpeed())
			return entity1;
		else
			return entity2;

	}
	
	// Función privada para aumentar la experiencia en +10
	private CharacterEntity getExp(CharacterEntity entity) {
		long acquired = entity.getExperience() + 10;
		if (acquired >= 100) {
			entity = levelUp(entity, acquired);
		} else
			entity.setExperience(acquired);
		return entity;
	}
	
	// Función privada para subir de nivel y aumentar todos los stats en +1
	private CharacterEntity levelUp(CharacterEntity entity, Long acquired) {
		
		entity.setExperience(acquired - 100);
		entity.setLvl(entity.getLvl() + 1);
		
		if(entity.getMagic() < 100)
			entity.setMagic(entity.getMagic() + 1);
		
		if(entity.getStrength() < 100)
			entity.setStrength(entity.getStrength() + 1);
		
		if(entity.getSpeed() < 100)
			entity.setSpeed(entity.getSpeed() + 1);
		
		if(entity.getHp() < 100)
			entity.setHp(entity.getHp() + 1);
		
		if(entity.getIntelligence() < 100)
			entity.setIntelligence(entity.getIntelligence() + 1);
		
		return entity;
		
	}
	
	//---------------------------------------------COMBATE MANUAL--------------------------------------------------------------
	public FightDto controlFight(ManualFightDto mFight) {
		
		//el primero es el user, que por defectoe empezara la lucha
		CharacterEntity entityPlayer1 = characterRepository.findById(mFight.getUser().getIdCharacter()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CHARACTER_NO_CONTENT);
			throw new CharacterNoContentException(DataErrorMessages.CHARACTER_NO_CONTENT);
		});

		CharacterEntity entityPlayer2 = characterRepository.findById(mFight.getOpponent().getIdCharacter()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CHARACTER_NO_CONTENT);
			throw new CharacterNoContentException(DataErrorMessages.CHARACTER_NO_CONTENT);
		});


		CharacterDto player1 = etd.getCharacter(entityPlayer1);
		CharacterDto player2 = etd.getCharacter(entityPlayer2);
		
		//Cambiamos los valores de HP por los actuales de los dtos
		player1.setHp(mFight.getNewHP1());
		player2.setHp(mFight.getNewHP2());

		SkillDto player1Skill;
		SkillDto player2Skill;
		
		List<SkillDto> movesPlayer1 = new ArrayList<SkillDto>();
		List<SkillDto> movesPlayer2 = new ArrayList<SkillDto>();
		List<SkillDto> allMoves = new ArrayList<SkillDto>();
		
		Integer random = 0;
		Integer cont = 1;

		//Aqui quito el while porque solo me interesa que lo haga 1 vez por turno

			System.out.println();
			System.out.println("TURNO " + cont + " -- " + player1.getName() + ": " + player1.getHp() + " HP -- " + player2.getName() + ": " + player2.getHp() + " HP");
			System.out.println("------------------------------------------------------------------");


			player1Skill = mFight.getMoveUser(); //El escogido por el usuario

			random = (int) (5 * Math.random());
			player2Skill = player2.getCharacterRol().getSkills().get(random);
			
			fight.setMoveOpponent(player2Skill);
			System.out.println("Movimiento oponente: " + player2Skill.getName());

			movesPlayer1.add(player1Skill);
			movesPlayer2.add(player2Skill);
			
			allMoves.add(player1Skill);
			allMoves.add(player2Skill);
			
			fight.setMovesPlayer1(movesPlayer1);
			fight.setMovesPlayer2(movesPlayer2);
			fight.setAllMoves(allMoves);
			fight.setFaster(etd.getCharacter(higherSpeed(mFight.getUser().getIdCharacter(), mFight.getOpponent().getIdCharacter()))); //Realmente el mas rapido
			
			// Si el jugador 1 usa una técnica de ATAQUE y ...
			if (player1Skill.getAction().toString().equals("ATAQUE")) {

				System.out.println(player1.getName() + " ha usado " + player1Skill.getName());


				// ... el jugador 2 usa una técnica de ATAQUE
				if(player2Skill.getAction().toString().equals("ATAQUE")) {

					System.out.println(player2.getName() + " ha perdido " + damage(player1Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() - damage(player1Skill.getQuantity(), player2.getHp()));
					
					fight.setHp2(player2.getHp()); //NUEVO HP2

					if (isGameOver(player2.getHp())) {
						entityPlayer1.setVictories(entityPlayer1.getVictories() + 1);
						entityPlayer2.setDefeats(entityPlayer2.getDefeats() + 1);
						characterRepository.save(getExp(entityPlayer1));
						characterRepository.save(entityPlayer2);
						fight.setWinner(etd.getCharacter(entityPlayer1));
						
						fight.setHp2((long) 0); //NUEVO HP2
	
						return fight;
					}

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName());

					System.out.println(player1.getName() + " ha perdido " + damage(player2Skill.getQuantity(), player1.getHp()) + " puntos de vida");

					player1.setHp(player1.getHp() - damage(player2Skill.getQuantity(), player1.getHp()));
					
					fight.setHp1(player1.getHp()); //NUEVO HP1

					if (isGameOver(player1.getHp())) {
						entityPlayer2.setVictories(entityPlayer2.getVictories() + 1);
						entityPlayer1.setDefeats(entityPlayer1.getDefeats() + 1);
						characterRepository.save(entityPlayer1);
						characterRepository.save(getExp(entityPlayer2));
						fight.setWinner(etd.getCharacter(entityPlayer2));
						
						
						fight.setHp1((long) 0); //NUEVO HP1
						
						return fight;
					}

				}



				// ... el jugador 2 usa una técnica de DEFENSA
				if(player2Skill.getAction().toString().equals("DEFENSA")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " para defenderse");

					System.out.println(player2.getName() + " ha perdido " + def(player1Skill.getQuantity(), player2Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() - def(player1Skill.getQuantity(), player2Skill.getQuantity(), player2.getHp()));
					
					fight.setHp2(player2.getHp()); //NUEVO HP2

					if (isGameOver(player2.getHp())) {
						entityPlayer1.setVictories(entityPlayer1.getVictories() + 1);
						entityPlayer2.setDefeats(entityPlayer2.getDefeats() + 1);
						characterRepository.save(getExp(entityPlayer1));
						characterRepository.save(entityPlayer2);
						fight.setWinner(etd.getCharacter(entityPlayer1));
						
						System.out.println("Al devolver: " + fight.getMoveOpponent());
						
						fight.setHp2((long) 0); //NUEVO HP2
						
						return fight;
					}

				}



				// ... el jugador 2 usa una técnica de CURA
				if(player2Skill.getAction().toString().equals("CURA")) {

					System.out.println(player2.getName() + " ha perdido " + damage(player1Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() - damage(player1Skill.getQuantity(), player2.getHp()));
					
					fight.setHp2(player2.getHp()); //NUEVO HP2

					if (isGameOver(player2.getHp())) {
						entityPlayer1.setVictories(entityPlayer1.getVictories() + 1);
						entityPlayer2.setDefeats(entityPlayer2.getDefeats() + 1);
						characterRepository.save(getExp(entityPlayer1));
						characterRepository.save(entityPlayer2);
						fight.setWinner(etd.getCharacter(entityPlayer1));
						
						System.out.println("Al devolver: " + fight.getMoveOpponent());

						fight.setHp2((long) 0); //NUEVO HP2
						return fight;
					}

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " y se ha curado " + heal(player2Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() + heal(player2Skill.getQuantity(), player2.getHp()));
					fight.setHp2(player2.getHp()); //NUEVO HP2

				}

			}




			// Si el jugador 1 usa una técnica de DEFENSA y ...
			else if (player1Skill.getAction().toString().equals("DEFENSA")) {

				System.out.println(player1.getName() + " ha usado " + player1Skill.getName() + " para defenderse");


				// ... el jugador 2 usa una técnica de ATAQUE
				if(player2Skill.getAction().toString().equals("ATAQUE")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName());

					System.out.println(player1.getName() + " ha perdido " + def(player2Skill.getQuantity(), player1Skill.getQuantity(), player1.getHp()) + " puntos de vida");

					player1.setHp(player1.getHp() - def(player2Skill.getQuantity(), player1Skill.getQuantity(), player1.getHp()));

					fight.setHp1(player1.getHp()); //NUEVO HP1
					
					if (isGameOver(player1.getHp())) {
						entityPlayer2.setVictories(entityPlayer2.getVictories() + 1);
						entityPlayer1.setDefeats(entityPlayer1.getDefeats() + 1);
						characterRepository.save(entityPlayer1);
						characterRepository.save(getExp(entityPlayer2));
						fight.setWinner(etd.getCharacter(entityPlayer2));
						

						
						fight.setHp1((long) 0); //NUEVO HP1
						return fight;
					}

				}



				// ... el jugador 2 usa una técnica de DEFENSA
				if(player2Skill.getAction().toString().equals("DEFENSA")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " para defenderse");

				}



				// ... el jugador 2 usa una técnica de CURA
				if(player2Skill.getAction().toString().equals("CURA")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " y se ha curado " + heal(player2Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() + heal(player2Skill.getQuantity(), player2.getHp()));
					
					fight.setHp2(player2.getHp()); //NUEVO HP2
					

				}

			}




			// Si el jugador 1 usa una técnica de CURA y ...
			else if (player1Skill.getAction().toString().equals("CURA")) {

				System.out.println(player1.getName() + " ha usado " + player1Skill.getName() + " y se ha curado " + heal(player1Skill.getQuantity(), player1.getHp()) + " puntos de vida");

				player1.setHp(player1.getHp() + heal(player1Skill.getQuantity(), player1.getHp()));


				// ... el jugador 2 usa una técnica de ATAQUE
				if(player2Skill.getAction().toString().equals("ATAQUE")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName());

					System.out.println(player1.getName() + " ha perdido " + def(player2Skill.getQuantity(), player1Skill.getQuantity(), player1.getHp()) + " puntos de vida");

					player1.setHp(player1.getHp() - def(player2Skill.getQuantity(), player1Skill.getQuantity(), player1.getHp()));
					
					fight.setHp1(player1.getHp()); //NUEVO HP1

					if (isGameOver(player1.getHp())) {
						entityPlayer2.setVictories(entityPlayer2.getVictories() + 1);
						entityPlayer1.setDefeats(entityPlayer1.getDefeats() + 1);
						characterRepository.save(entityPlayer1);
						characterRepository.save(getExp(entityPlayer2));
						fight.setWinner(etd.getCharacter(entityPlayer2));
						
						fight.setHp1((long) 0); //NUEVO HP1
						
						return fight;
					}

				}



				// ... el jugador 2 usa una técnica de DEFENSA
				if(player2Skill.getAction().toString().equals("DEFENSA")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " para defenderse");

				}



				// ... el jugador 2 usa una técnica de CURA
				if(player2Skill.getAction().toString().equals("CURA")) {

					System.out.println(player2.getName() + " ha usado " + player2Skill.getName() + " y se ha curado " + heal(player2Skill.getQuantity(), player2.getHp()) + " puntos de vida");

					player2.setHp(player2.getHp() + heal(player2Skill.getQuantity(), player2.getHp()));
					
					fight.setHp2(player2.getHp()); //NUEVO HP2

				}

			}
			cont++;
			fight.setHp1(player1.getHp());
			fight.setHp2(player2.getHp());
			return fight;
	}

	
	
	
	

}
