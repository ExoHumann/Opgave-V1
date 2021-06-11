
/* Need to switch of FK check for MySQL since there are crosswise FK references */
SET FOREIGN_KEY_CHECKS = 0;;



CREATE TABLE IF NOT EXISTS Game (
  gameID int NOT NULL UNIQUE AUTO_INCREMENT,

  name varchar(255),

  phase tinyint,
  step tinyint,
  currentPlayer tinyint NULL,

  PRIMARY KEY (gameID),
  FOREIGN KEY (gameID, currentPlayer) REFERENCES Player(gameID, playerID)


) ENGINE = InnoDB
;;

CREATE TABLE IF NOT EXISTS Player (
  gameID int NOT NULL,
  playerID tinyint NOT NULL,

  name varchar(255),
  colour varchar(31),

  positionX int,
  positionY int,
  heading tinyint,

  PRIMARY KEY (gameID, playerID),
  FOREIGN KEY (gameID) REFERENCES Game(gameID)
)ENGINE = InnoDB;;



CREATE TABLE IF NOT EXISTS playerHand (
  playerID int NOT NULL,
  gameID tinyint NOT NULL,

  card0 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
  card1 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
  card2 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
  card3 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
  card4 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
  card5 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
  card6 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
  card7 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
  PRIMARY KEY (`playerID`, `gameID`)



)ENGINE = InnoDB;;


CREATE TABLE IF NOT EXISTS playerRegister (
  playerID INT NOT NULL,
  gameID INT NOT NULL,

   card0 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
   card1 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
   card2 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
   card3 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,
   card4 ENUM("FORWARD", "RIGHT", "LEFT", "FAST_FORWARD", "OPTION_LEFT_RIGHT") NULL,

    PRIMARY KEY (playerID, gameID)
)ENGINE = InnoDB;;








SET FOREIGN_KEY_CHECKS = 1;;

