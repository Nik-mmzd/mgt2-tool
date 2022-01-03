import com.charleskorn.kaml.*
import pw.modder.mgt2helper.data.*
import pw.modder.mgt2helper.data.Targets.*
import java.time.*

fun main() {
    val genres = Genres(
        Instant.now().epochSecond,
        listOf(
            Genre(
                "Skill Game",
                listOf(Children, Teenagers, Adults, Seniors, All),
                Design(40, 10, 10, 40),
                listOf(
                    Subgenre(
                        "None",
                        Directions(5, 2, 8, 5, 2, 3, 8, 7, 7, 1, 7),
                        true
                    ),
                    Subgenre(
                        "Puzzle Game",
                        Directions(7, 3, 7, 4, 3, 2, 7, 7, 7, 0, 7),
                        true
                    ),
                    Subgenre(
                        "Racing",
                        Directions(7, 4,8, 5, 1, 2, 8, 5, 7, 0, 5)
                    )
                ),
                "Agriculture, Animals, Archaeology, Art, Baseball, Basketball, Birds, Building Blocks, Candy, Cars, Casinos, Cats, Chemistry, Chickens, Cities, Climbing, Cooking, Cyberpunk, Cyberspace, Dancing, Deck Building, Digging, Diving, Dogs, Dragons, Drawing, Dreams, Education, Elements, Espionage, Everyday Life, Evolution, Expeditions, Factories, Fairies, Fantasy, Fashion, Fish, Fishing, Fitness, Food, Frogs, Fruits, Gambling, Ghosts, Goats, Hacking, Handball, Healthcare, Hospital, Insects, Jet Ski, Kids, Mathematics, Middle Ages, Mining, Movies, Mushrooms, Music, Octopus, Orcs, Parallel Worlds, Parlor Games, Physics, Pinball, Planets, Plants, Playing Cards, Plumber, Portals, Prison, Puzzles, Quiz Show, Rabbits, Renovation, Riding, Rockstars, Sailing, School, Science, Sheep, Singing, Skydiving, Soccer, Skiing, Space, Sports, Steampunk, Stone Age, Stones, Swimming, Table Tennis, Tennis, Theme Parks, Thieves, Time Travel, Toys, Trains, Transportation, Treasure, Viruses, Volleyball, Wizards, Worms, Zoo, Pets".split(", ")
            ),
            Genre(
                "Skill Game",
                listOf(Children, Teenagers, Adults, Seniors, All),
                Design(40, 10, 10, 40),
                listOf(
                    Subgenre(
                        "None",
                        Directions(5, 2, 8, 5, 2, 3, 8, 7, 7, 1, 7),
                        true
                    ),
                    Subgenre(
                        "Puzzle Game",
                        Directions(7, 3, 7, 4, 3, 2, 7, 7, 7, 0, 7),
                        true
                    ),
                    Subgenre(
                        "Racing",
                        Directions(7, 4,8, 5, 1, 2, 8, 5, 7, 0, 5)
                    )
                ),
                "Agriculture, Animals, Archaeology, Art, Baseball, Basketball, Birds, Building Blocks, Candy, Cars, Casinos, Cats, Chemistry, Chickens, Cities, Climbing, Cooking, Cyberpunk, Cyberspace, Dancing, Deck Building, Digging, Diving, Dogs, Dragons, Drawing, Dreams, Education, Elements, Espionage, Everyday Life, Evolution, Expeditions, Factories, Fairies, Fantasy, Fashion, Fish, Fishing, Fitness, Food, Frogs, Fruits, Gambling, Ghosts, Goats, Hacking, Handball, Healthcare, Hospital, Insects, Jet Ski, Kids, Mathematics, Middle Ages, Mining, Movies, Mushrooms, Music, Octopus, Orcs, Parallel Worlds, Parlor Games, Physics, Pinball, Planets, Plants, Playing Cards, Plumber, Portals, Prison, Puzzles, Quiz Show, Rabbits, Renovation, Riding, Rockstars, Sailing, School, Science, Sheep, Singing, Skydiving, Soccer, Skiing, Space, Sports, Steampunk, Stone Age, Stones, Swimming, Table Tennis, Tennis, Theme Parks, Thieves, Time Travel, Toys, Trains, Transportation, Treasure, Viruses, Volleyball, Wizards, Worms, Zoo, Pets".split(", ")
            ),
            Genre(
                "Skill Game",
                listOf(Children, Teenagers, Adults, Seniors, All),
                Design(40, 10, 10, 40),
                listOf(
                    Subgenre(
                        "None",
                        Directions(5, 2, 8, 5, 2, 3, 8, 7, 7, 1, 7),
                        true
                    ),
                    Subgenre(
                        "Puzzle Game",
                        Directions(7, 3, 7, 4, 3, 2, 7, 7, 7, 0, 7),
                        true
                    ),
                    Subgenre(
                        "Racing",
                        Directions(7, 4,8, 5, 1, 2, 8, 5, 7, 0, 5)
                    )
                ),
                "Agriculture, Animals, Archaeology, Art, Baseball, Basketball, Birds, Building Blocks, Candy, Cars, Casinos, Cats, Chemistry, Chickens, Cities, Climbing, Cooking, Cyberpunk, Cyberspace, Dancing, Deck Building, Digging, Diving, Dogs, Dragons, Drawing, Dreams, Education, Elements, Espionage, Everyday Life, Evolution, Expeditions, Factories, Fairies, Fantasy, Fashion, Fish, Fishing, Fitness, Food, Frogs, Fruits, Gambling, Ghosts, Goats, Hacking, Handball, Healthcare, Hospital, Insects, Jet Ski, Kids, Mathematics, Middle Ages, Mining, Movies, Mushrooms, Music, Octopus, Orcs, Parallel Worlds, Parlor Games, Physics, Pinball, Planets, Plants, Playing Cards, Plumber, Portals, Prison, Puzzles, Quiz Show, Rabbits, Renovation, Riding, Rockstars, Sailing, School, Science, Sheep, Singing, Skydiving, Soccer, Skiing, Space, Sports, Steampunk, Stone Age, Stones, Swimming, Table Tennis, Tennis, Theme Parks, Thieves, Time Travel, Toys, Trains, Transportation, Treasure, Viruses, Volleyball, Wizards, Worms, Zoo, Pets".split(", ")
            )
        )
    )

    val string = Yaml(configuration = YamlConfiguration(encodeDefaults = false)).encodeToString(Genres.serializer(), genres)
    println(string)
}