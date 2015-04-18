package com.company;

public class createCharacter{

    Monster character;
    private readJTextField readConsole;
    private writeJTextPane writeTextPane;
    private String playerStatsToShow = "";
    private playerStats playerStats = new playerStats();

    public Monster createNewCharacter(){
        do {
            getAttributes();
        }
        while (isSkillsPointsOverRange());
        setSpecialization();
        character = new Monster(playerStats.health, 2, playerStats.name, playerStats.skill_at_attack, playerStats.skill_at_magic, playerStats.specialization, true);
        playerStatsToShow += "\n\n GOOD LUCK  " + playerStats.name.toUpperCase();
        showStats();
        return character;
    }

    public boolean wantToCreateAnother(){
        writeTextPane.setText("\nCreate another character ?");

        waitForAction();
        return readConsole.getText().equals("t");
    }

    public createCharacter(writeJTextPane writer, readJTextField reader){
        writeTextPane = writer;
        readConsole = reader;
    }

    private void getAttributes(){
        setName();
        setHealth();
        setAttackSkills();
        setMagicSkills();
    }

    private boolean isSkillsPointsOverRange(){
        if (playerStats.health + playerStats.skill_at_attack + playerStats.skill_at_magic > 170) {
            writeTextPane.setText("Wrong amount of skillpoints, create again ");
            wait(2000);
            return true;
        }
        else return false;
    }

    private void setSpecialization(){
        playerStatsToShow +="\nLast question: fighting style of your character (30-70, higher number - melee weapon specialist, lesser number - blood mage):  ";
        showStats();
        playerStats.specialization = getInt();

        if (playerStats.specialization < 50) {
            playerStatsToShow += "\nSpecialization: Blood Mage ! ";
        } else {
            playerStatsToShow += "\nSpecialization: Warrior ! ";
        }
        showStats();
        wait(1000);
    }

    private void showStats(){
        writeTextPane.setText(playerStatsToShow);
    }

    private void setName(){
        playerStatsToShow = "Name: ";
        showStats();
        playerStats.name = getString();
        showUpdatedStats();
    }

    private void setHealth(){
        playerStatsToShow += "\nHealth: ";
        showStats();
        playerStats.health = getInt();
        showUpdatedStats();
    }

    private void setAttackSkills(){
        playerStatsToShow += "\nFighting skill: ";
        showStats();
        playerStats.skill_at_attack = getInt();
        showUpdatedStats();
    }

    private void setMagicSkills(){
        playerStatsToShow += "\nMagic skill: ";
        showStats();
        playerStats.skill_at_magic = getInt();
        showUpdatedStats();
    }

    private String getString(){
        waitForAction();
        return readConsole.getText();
    }

    private int getInt() {
        int parsedInt = 0;
        boolean successfulParsing = false;

        while (!successfulParsing) {
            try {
                waitForAction();
                parsedInt = readConsole.tryToGetInt();
                successfulParsing = true;
            } catch (NumberFormatException e) {
                writeTextPane.setText("Not a number");
                wait(1000);
                showStats();
            }
        }
        return parsedInt;
    }

    private void showUpdatedStats(){
        playerStatsToShow += readConsole.getText();
        showStats();
    }

    private void waitForAction() {
        while (!readConsole.isActionPerformed()){
            wait(100);
        }
    }

    private void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException ignored){}
    }

}
