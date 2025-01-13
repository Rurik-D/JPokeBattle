package it.rd.jpokebattle.controller.battle;

import it.rd.jpokebattle.controller.Controller;
import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.controller.SceneManager;
import it.rd.jpokebattle.controller.arcade.ArcadeController;
import it.rd.jpokebattle.controller.arcade.ArcadeNodeManager;
import it.rd.jpokebattle.controller.menu.MenuNodeManager;
import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.move.MoveCategory;
import it.rd.jpokebattle.model.move.MoveSpecialEffect;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;
import it.rd.jpokebattle.model.pokemon.Stats;
import it.rd.jpokebattle.util.audio.SoundManager;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe controller per la gestione della scena di lotta.
 */
public final class BattleController extends Controller {
    private static BattleNodeManager nodeMan = BattleNodeManager.getInstance();
    private SoundManager soundMan = SoundManager.getInstance();
    private PauseTransition delay_3_0 = new PauseTransition(Duration.seconds(3.0));
    private PauseTransition delay2_3_0 = new PauseTransition(Duration.seconds(3.0));
    private PauseTransition delay_2_0 = new PauseTransition(Duration.seconds(2.0));
    private PauseTransition delay2_2_0 = new PauseTransition(Duration.seconds(2.0));
    private PauseTransition delay3_2_0 = new PauseTransition(Duration.seconds(2.0));
    private PauseTransition delay4_2_0 = new PauseTransition(Duration.seconds(2.0));
    private PauseTransition delay_1_5 = new PauseTransition(Duration.seconds(1.5));
    private HashMap<Pokemon, Map<Stats, Double>> stats = new HashMap<>();
    private OwnedPokemon playerPokemon;
    private Pokemon opponentPokemon;
    private Turn nextAttacker;
    private boolean running;
    private boolean firstAttackDone;
    private boolean hesitation;
    private boolean protect;
    private int moveToForgetIndex;
    private int currentPlayerPkmn = 0;
    private Move newMove;
    private boolean waitForPkmnChange;

    @FXML
    protected Button
            teamPaneBackBtn, changePkmnBtn, cancelChangePkmnBtn;

    @FXML
    protected GridPane
            rootPane, labelsPane, buttonsPane, bagPane, teamPane,
            newMovePane, forgetMoveBtnPane, gameSettingsPane,
            pokemonInfoPane, moveInfoPane, pkmnChangePane;

    @FXML
    protected ScrollPane logScrollPane;

    @FXML
    protected FlowPane movesPane, teamCardsPane, updateMovesPane;

    @FXML
    protected HBox pokeCounterBox;

    @FXML
    protected ImageView playerPkmnGif, opponentPkmnGif, transitionGif, info_avatarImgView;

    @FXML
    protected Label
            playerPkmnNameLbl, opponentPkmnNameLbl, logLbl, info_nameLbl, info_lvLbl, info_xpLbl, info_hpLbl, info_attLbl,
            info_difLbl, info_sAttLbl, info_sDifLbl, info_velLbl, moveDescriptionLbl,
            moveTypeLbl, moveCatLbl, movePowLbl, movePriorityLbl, movePrecLbl, movPPLbl;

    @FXML
    protected VBox pokemonMovesVBox;


    /**
     *  Metodo chiamato dal bottone 'fuga'.
     *  Termina l'incontro e riporta all'ultima area sicura nella modalita arcade.
     *
     * @param e Click del mouse
     */
    @FXML
    public void escape(MouseEvent e) {
        soundMan.buttonClick();
        getPlayer().goToLastSafeArea();
        switchToArcadeScene();
    }

    /** TODO DA IMPLEMENTARE
     *  Metodo chiamato dal bottone 'borsa'.
     *  Apre la schermata dello zaino per la selezione degli item.
     *
     * @param e Click del mouse
     */
    @FXML
    public void openBag(MouseEvent e) {
        soundMan.buttonClick();
        bagPane.setVisible(true);
    }

    /**
     * Metodo chiamato dal bottone 'pkmn'.
     * Apre la schermata di visualizzazione del team, dove è anche possibile scambiare
     * il pokemon attualmente in lotta con un altro pokemon del team.
     *
     * @param e Click del mouse
     */
    @FXML
    public void pkmnTeam(MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showTeamPane(getPlayer());
    }

    /**
     * Torna alla schermata di visualizzazione dei dettagli del pokemon.-
     *
     * @param e Azione generica sul bottone.
     */
    @FXML
    public void backToPokemonDetails(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.showPokemonDetails();
        moveInfoPane.getChildren().removeLast();
    }


    /**
     * Mostra la schermata dettagli del pokemon.
     *
     * @param e Click del mouse.
     */
    public void pokemonDetails(MouseEvent e, OwnedPokemon pkmn) {
        soundMan.buttonClick();
        nodeMan.showPokemonDetails(pkmn);
    }

    /**
     * Metodo chiamato dal bottone "Mantieni le vecchie mosse" all'interno del pannello di
     * aggiunta di una nuova mossa appresa. La mossa non viene appresa e si ritorna alla scena
     * di arcade. Quest'ultimo passaggio viene fatto dando per scontata la vittoria del pokemon,
     * in quanto per apprendere una nuova mossa deve salire di livello e ciò è possibile esclusivamente
     * vincendo la lotta.
     *
     * @param e Click del mouse.
     */
    @FXML
    public void keepOldMoves(MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.backToGame();
        endGame(true);
    }

    /**
     * Il pokemon del giocatore dimentica la mossa selezionata in precedenza e aggiunge alle mosse conosciute
     * nuova mossa imparata al passaggio di livello.
     *
     * @param e Click del mouse.
     */
    @FXML
    public void forgetMove(MouseEvent e) {
        soundMan.buttonClick();
        playerPokemon.replaceMove(moveToForgetIndex, newMove);

        nodeMan.backToGame();
        endGame(true);
    }

    /**
     * Bottone che nasconde i pannelli in sovrimpressione e riporta alla schermata di lotta.
     *
     * @param e Click del mouse.
     */
    @FXML
    public void backToGame(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.backToGame();
    }

    /**
     * Mostra la schermata di impostazioni.
     *
     * @param e Click del mouse.
     */
    @FXML
    public void settings (MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showSettingsPane();
    }

    /**
     * Torna al menù principale, carica la nuova scena, aggiorna la root nella classe
     * astratta NodeManager e aggiorna il file dei profili.
     *
     * @param e Azione generica sul bottone.
     * */
    @FXML
    public void mainMenu(ActionEvent e) throws IOException {
        soundMan.buttonClick();
        soundMan.switchTrack("mp3.title");

        FXMLLoader loader = SceneManager.switchScene(e, "fxml.menu", "css.menu");
        NodeManager.setRoot(loader.getRoot());
        MenuNodeManager.setController(loader.getController());

    }

    /**
     * Switch del volume.
     *
     * @param e Azione generica sul bottone.
     */
    @FXML
    public void volume(ActionEvent e) {
        soundMan.buttonClick();
        soundMan.toggleVolume();
    }

    /**
     * Torna alla schermata di visualizzazione dei pokemon.
     *
     * @param e Azione generica sul bottone.
     */
    @FXML
    public void backToOwnedTeam(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.backToShowTeam();
    }

    /**
     * Apre la schermata di selezione del pokemon da cambiare.
     *
     * @param e Azione generica sul bottone.
     */
    @FXML
    public void changePkmn_view(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.showChangePkmnView();
    }

    /**
     * Annulla la selezione del pokemon con cui cambiare e torna
     * alla schermata di visualizzazione del team.
     *
     * @param e Azione generica sul bottone.
     */
    @FXML
    public void cancelPkmnChange(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.hideChangePkmnView();
    }


    /**
     * Selezione del pokemon con cui eseguire
     *
     * @param e Azione generica sul bottone.
     */
    @FXML
    public void changePlayerPokemon(ActionEvent e) {
        soundMan.buttonClick();
        int selectedPkmnIndex = Integer.parseInt(((Node) e.getSource()).getId().substring(1));

        if (selectedPkmnIndex != currentPlayerPkmn && selectedPkmnIndex < getPlayer().getTeam().size()) {
            currentPlayerPkmn = selectedPkmnIndex;
            playerPokemon = getPlayer().getTeam().get(selectedPkmnIndex);
            nodeMan.updatePlayerPokemonGraphics(playerPokemon);
            nodeMan.backToGame();
            battlePhase(-1, getOpponentMoveIndex());
            waitForPkmnChange = false;
            running = true;
        }
    }

    /**
     * Inizializza variabili chiave e nodi della scena.
     *
     * @param oppPkmn Pokemon avversario
     */
    public void initializeWildBattleScene(Pokemon oppPkmn) {
        opponentPokemon = oppPkmn;
        playerPokemon = getPlayer().getFirstPokemon();
        running = true;
        setStats();

        nodeMan.initializeNodes(playerPokemon, opponentPokemon);
        nodeMan.updatePokeCounterBox(getPlayer());
    }

    /**
     *  Durante la lotta i cambiamenti sulle statistiche sono temporanei e si resettano quando la
     *  lotta finisce.
     *  Per ottenere questo utilizziamo una mappa contente a sua volta una mappa per ogni pokemon presente
     *  nella lotta (compresi quelli nel team del giocatore non attualmente in combattimento, in tal modo le
     *  modifiche alle statistiche sono preservate durante i cambi) dove andiamo a impostare le statistiche
     *  temporanee alle statistiche originali dei pokemon.
     */
    private void setStats() {
        stats.put(opponentPokemon, new HashMap<>());

        for (OwnedPokemon pkmn : getPlayer().getTeam())
            stats.put(pkmn, new HashMap<>());

        for (Stats stat : Stats.values()) {
            for (Pokemon pkmn : stats.keySet())
                stats.get(pkmn).put(stat, (double) pkmn.getStat(stat));
        }
    }

    /**
     * Imposta l'indice della mossa da dimenticare con l'indice passato in input.
     *
     * @param moveToForget Indice della mossa da dimenticare.
     */
    public void setMoveToForgetIndex(int moveToForget) {
        this.moveToForgetIndex = moveToForget;
    }

    /**
     * Dato un pokemon, tra i due in gioco, ed una statistica in particolare, ritorna il valore temporaneo
     * (che ha valore unicamente nel corso della lotta corrente) associato a quella statistica per quel
     * pokemon.
     *
     * @param pkmn Pokemon del quale si vuole selezionare una statistica
     * @param stat Statistica da selezionare
     * @return Statistica selezionata per il relativo pokemon
     */
    private double getStat(Pokemon pkmn, Stats stat) {
        return stats.get(pkmn).get(stat);
    }

    /**
     * Ogni statistica (al di fuori dalla salute) può subire una variazione positiva o negativa fino al 50%.
     * Ogni variazione modifica la statistica positivamente o negativamente di val * (10% * stat_originaria).
     *
     * @param pkmn Pokemon del quale si vuole aggiornare una certa statistica
     * @param stat Statistica da aggiornare
     * @param val Valore da aggiungere (o rimuovare se negativo) in percentuale rispetto alla statistica originaria
     */
    private void updateStat(Pokemon pkmn, Stats stat, double val) {
        double offset = 10 * val;

        if (stat == Stats.HP)
            pkmn.takeDamage((double) pkmn.getMaxHP() / offset);
        else {
            double statVal = getStat(pkmn, stat);
            double valPercentage = statVal / offset;
            double newVal = valPercentage > 0 ? Math.ceil(statVal + valPercentage) : Math.floor(statVal + valPercentage);

            double min = Math.ceil((double) pkmn.getStat(stat) / 100 * 50);
            double max = Math.ceil((double) pkmn.getStat(stat) / 100 * 150);

            if (newVal < min)
                nodeMan.updateLogLbl(String.format("%s di %s non può diminuire ancora", stat.getFormattedName(), pkmn.getName()));
            else if (newVal > max)
                nodeMan.updateLogLbl(String.format("%s di %s non può aumentare ancora", stat.getFormattedName(), pkmn.getName()));
            else {
                String changeText =  newVal < statVal ? "diminuisce" : "aumenta";
                nodeMan.updateLogLbl(String.format("%s di %s %s", stat.getFormattedName(), pkmn.getName(), changeText));
                stats.get(pkmn).put(stat, newVal);
            }
        }
    }

    /**
     * Metodo chiamato a seguito del click del mouse su uno dei 4 Grid Pane delle mosse.
     * Viene calcolato l'indice della mossa e, se la mossa ha PP > 0, viene calcolata
     * randomicamente la mossa dell'avversario e inizia un nuovo round.
     *
     * @param e Click del mouse
     */
    public void move(MouseEvent e) {
        Node moveCard = (Node) e.getSource();
        int playerMoveIndex = getMoveIndexFromPaneID(moveCard.getId());
        int opponentMoveIndex;

        if (isMoveAvailable(playerPokemon, playerMoveIndex)) {
            soundMan.buttonClick();
            resetSpecialEffects();
            opponentMoveIndex = getOpponentMoveIndex();
            battlePhase(playerMoveIndex, opponentMoveIndex);
        }
    }

    /**
     * La Battle Phase consta è scandita dai due attacchi (uno per pokemon) seguiti ogni volta
     * dal controllo che il pokemon attaccato sia ancora vivo (e che quindi sia terminata quella
     * lotta).
     * Attacca prima il pokemon con la mossa avente priorità più alta, in caso di parità quello
     * più veloce.
     *
     * @param plMvIndex Indice della mossa (tra le 4) selezionata dal giocatore
     * @param oppMvIndex Indice della mossa (tra le 4) selezionata dall'avversario
     */
    private void battlePhase(int plMvIndex, int oppMvIndex) {
        buttonsPane.setDisable(true);
        updateNextAttacker(plMvIndex, oppMvIndex);
        boolean isSecondMove = false;

        for (int i=0; i<2; i++) {
            switch (nextAttacker) {
                case PLAYER:
                    makeMove(playerPokemon, opponentPokemon, plMvIndex, isSecondMove);
                    nextAttacker = Turn.OPPONENT;
                    break;
                case OPPONENT:
                    makeMove(opponentPokemon, playerPokemon, oppMvIndex, isSecondMove);
                    nextAttacker = Turn.PLAYER;
                    break;
            }
            isSecondMove = true;
        }
    }

    /**
     * Metodo in cui viene messo in atto l'effetto della mossa.
     * Tale metodo viene chiamato al più due volte per ogni Battle Phase (uno per pokemon in gioco).
     * Ogni attacco viene prima annunciato nella log label e successivamente (dopo un ritardo di
     * 1.5 secondi) viene calcolato prima se il colpo va a segno. In caso affermativo vengono
     * calcolati e applicati i danni.
     * Se il pokemon sta attaccando per secondo l'annuncio dell'attacco viene ritardato di ulteriori
     * 3 secondi (per permettere all'animazione della barra della vita di concludersi);
     * successivamente dopo aver effettuato l'attacco viene applicato un ulteriore ritardo di 3 secondi
     * (stesso motivo).
     *
     * @param attPkmn Pokemon attaccante
     * @param defPkmn Pokemon difensore
     * @param moveIndex Indice della mossa (tra le 4) dell'attaccante
     * @param isSecondMove Valore booleano che specifica se l'attaccante sta attaccando per secondo
     */
    private void makeMove(Pokemon attPkmn, Pokemon defPkmn, int moveIndex, boolean isSecondMove) {
        if (moveIndex == -1) {
            if (isSecondMove)
                buttonsPane.setDisable(!running);
            else
                firstAttackDone = true;
            return;
        }

        if (isSecondMove) {
            delay_3_0.setOnFinished(e1 -> {
                if (!firstAttackDone)
                    delay_3_0.play();
                else {
                    makeMove(attPkmn, defPkmn, moveIndex, false);

                    delay2_3_0.setOnFinished(e2 -> {
                        buttonsPane.setDisable(!running);
                    });

                    delay2_3_0.play();
                }
            });
            delay_3_0.play();
        } else if (running){
            Move move = attPkmn.getMove(moveIndex);
            attPkmn.decresePP(moveIndex);
            nodeMan.updateLogLbl(attPkmn.getName() + " usa " + move.getName());
            soundMan.attackSound();

            delay_1_5.setOnFinished(e -> {
                if (striked(attPkmn, defPkmn, move)) { // controllo del colpo a segno
                    if (!checkSpecialEffects(attPkmn, defPkmn)) {
                        defPkmn.takeDamage(calcDamage(attPkmn, defPkmn, move)); // calcolo danno
                        applyStatsVariations(attPkmn, defPkmn, move);
                        applySpecialEffects(move);
                    }
                } else
                    nodeMan.updateLogLbl(move.getName() + " non ha colpito il bersaglio");
                firstAttackDone = true;
                checkEndConditions();
            });
            delay_1_5.play();
        }
    }

    /**
     * In base alla formula per il 'calcolo del colpo a segno' ((prec. attaccante / elus. difensore) * prec. mossa)
     * viene calcolato se il colpo va o meno a segno.
     *
     * @param attPkmn Pokemon attaccante
     * @param defPkmn Pokemon difensore
     * @param move Mossa eseguita dall'attaccante
     * @return Valore booleano che specifica se la mossa ha colpito o meno
     */
    private boolean striked(Pokemon attPkmn, Pokemon defPkmn, Move move) {
        double attPrec = getStat(attPkmn, Stats.PRECISION);
        double defElus = getStat(defPkmn, Stats.ELUSION);
        double movePrec = move.getPrecision();

        double formula = (attPrec / defElus) * movePrec;

        return rand.nextDouble(0, 105) < formula;
    }

    /**
     * Controlla se è attivo un effetto speciale che potrebbe andare a bloccare l'attacco corrente.
     * Tale effetto può essere tentennamento (l'attacante fallisce) o protezione (il difensore si
     * protegge).
     * Viene ritornato un valore booleano asserito nel caso in cui l'attacco debba essere interrotto.
     *
     * @param attPkmn Pokemon in attacco
     * @param defPkmn Pokemon in difesa
     * @return Valore booleano che specifica se l'attacco debba o meno essere interrotto
     */
    private boolean checkSpecialEffects(Pokemon attPkmn, Pokemon defPkmn) {
        boolean blockAttack = false;

        if (protect) {
            nodeMan.updateLogLbl(defPkmn.getName() + " si protegge");
            protect = false;
            blockAttack = true;
        } else if (hesitation) {
            nodeMan.updateLogLbl(attPkmn.getName() + " tentenna");
            hesitation = false;
            blockAttack = true;
        }

        return blockAttack;
    }

    /**
     * A seconda della tipologia della mossa (fisico, speciale o stato) viene calcolato il danno con la
     * seguente formula ((2 * lv / 5 + 2) * pow * atk / def) / 50 + 2.
     * Per mosse fisiche vengono considerati attacco e difesa normali.
     * Per mosse speciali vengono considerti att speciale e dif speciale.
     * Per mosse stato il danno è 0 a priori.
     *
     * @param attPkmn Pokemon attaccante
     * @param defPkmn Pokemon difensore
     * @param move Mossa eseguita dall'attaccante
     * @return Danno calcolato in base alla formula
     */
    private int calcDamage(Pokemon attPkmn, Pokemon defPkmn, Move move) {
        MoveCategory moveCat = MoveCategory.fromName(move.getCategory());
        double effectiveness = getEffectiveness(defPkmn, move);
        double lv = attPkmn.getLevel();
        double pow = move.getPower();
        int strikes = 1;
        double damage;
        double atk;
        double def;

        if (moveCat == MoveCategory.PHYSICAL) {
            atk = getStat(attPkmn, Stats.ATK);
            def = getStat(defPkmn, Stats.DEF);
        } else if (moveCat == MoveCategory.SPECIAL) {
            atk = getStat(attPkmn, Stats.SPEC_ATK);
            def = getStat(defPkmn, Stats.SPEC_DEF);
        } else
            return 0;

        damage = ((2 * lv / 5 + 2) * pow * atk / def) / 50 + 2;

        if (move.getSpecialEffect() == MoveSpecialEffect.MULTI_ATTACK) {
            strikes = rand.nextInt(1, 6);
            nodeMan.updateLogLbl(String.format("%s ha colpito %d volte", attPkmn.getName(), strikes) );
        }

        printEffectiveness(defPkmn.getName(), effectiveness);

        return (int) Math.ceil(strikes * damage * effectiveness * rand.nextDouble(0.85, 1.0001) * critic(attPkmn, move));
    }

    /**
     * Calcola il modificatore del colpo critico (intero [1, 2]) a partire dal modificatore della probabilità
     * del colpo critico dell'utente. Più tale modificatore è alto più la probabilità del colpo critico è alta.
     * La probabilità di critico di base è di circa il 10%. Se la mossa possiede l'effetto speciale "high_crit_prob"
     * tale probabilità si incrementa di circa il 30% oltre la probabilità base.
     *
     * @param attPkmn Pokemon che sta attaccando in questo turno
     * @param move Mossa utilizzata dal pokemon attaccante
     * @return Modificatore del critico (1 o 2)
     */
    private double critic(Pokemon attPkmn, Move move) {
        boolean highProb = move.getSpecialEffect() == MoveSpecialEffect.HIGH_CRIT_PROB;
        int pkmnCritProb = attPkmn.getStat(Stats.CRIT_PROB) + (highProb ? 40 : 10);
        int randVal = rand.nextInt(0, pkmnCritProb);

        if (randVal >= 100) {
            nodeMan.updateLogLbl("Colpo critico!");
            return 2; // Doppio del danno
        }

        return 1; // Danno normale
    }

    /**
     * Vengono applicate le variazioni di stato che una mossa può causare (all'ultilizzatore o all'avversario).
     * Ogni statistica non può variare in positivo o in negativo di più del 50% dal suo valore originale.
     *
     * @param attPkmn Pokemon attaccante
     * @param defPkmn Pokemon difensore
     * @param move Mossa eseguita dall'attaccante
     */
    private void applyStatsVariations(Pokemon attPkmn, Pokemon defPkmn, Move move) {
        Map<String, Double> oppStatsVariations = move.getOpponentStats();
        Map<String, Double> userStatsVariations = move.getUserStats();

        for (String stat : oppStatsVariations.keySet()) {
            double var = oppStatsVariations.get(stat);
            updateStat(defPkmn, Stats.fromName(stat), var);
        }

        for (String stat : userStatsVariations.keySet()) {
            double var = userStatsVariations.get(stat);
            updateStat(attPkmn, Stats.fromName(stat), var);
        }
    }

    /**
     * Vengono applicati gli (eventuali) effetti speciali della mossa dell'attaccante.
     * Se tale effetto è protezione, il prossimo attacco nel turno del pokemon avversario
     * (se avviene) verrà bloccato.
     * Se tale effetto è tentennamento, il prossimo attacco nel turno del pokemon avversario
     * (se avviene) potrebbe fallire.
     *
     * @param move
     */
    private void applySpecialEffects(Move move) {
        MoveSpecialEffect moveEffect = move.getSpecialEffect();
        protect = (moveEffect == MoveSpecialEffect.PROTECT);

        if (moveEffect == MoveSpecialEffect.HESITATION) {
            hesitation = (rand.nextInt(0, 100) < 30);
        }
    }

    /**
     * Gli efetti speciali delle mosse eventualmente ancora attivi vengono resettati.
     * (metodo chiamato ad ogni inizio turno)
     */
    private void resetSpecialEffects() {
        hesitation = false;
        protect = false;
    }

    /**
     * Viene calcolata e restituita l'efficacia della mossa utilizzata rispetto al tipo (o ai tipi) del
     * pokemon che difende.
     *
     * @param pkmn Pokemon che subisce l'attacco
     * @param move Mossa subita dal pokemon
     */
    private double getEffectiveness(Pokemon pkmn, Move move) {
        String type1 = pkmn.getBreed().getFirstTypeName();
        String type2 = pkmn.getBreed().getSecondTypeName();
        return move.getType().getTypeEff(type1, type2);
    }

    /**
     * Viene mostrata sulla log label l'efficacia della mossa utilizzata.
     *
     * @param defPkmnName Nome del pokemon che subisce l'attacco
     * @param eff Grado di efficacia della mossa
     */
    private void printEffectiveness(String defPkmnName, double eff) {
        if (eff >= 2)
            nodeMan.updateLogLbl("È super efficace");
        else if (eff >= 1.5)
            nodeMan.updateLogLbl("È molto efficace");
        else if (eff >= 1)
            nodeMan.updateLogLbl(defPkmnName + " è stato colpito");
        else if (eff > 0)
            nodeMan.updateLogLbl("Non è molto efficace...");
        else
            nodeMan.updateLogLbl(defPkmnName + " è immune a questo tipo di attacchi!");
    }

    /**
     * Controllate condizioni di vittoria e sconfitta. Eventualmente viene fermata l'esecuzione della
     * Battle Phase e viene chiamato il relativo metodo per il ritorno alla modalità arcade per vittoria
     * o per sconfitta.
     */
    private void checkEndConditions() {
        boolean allFainted = true;

        if (waitForPkmnChange)
            return;

        if (playerPokemon.isFainted()) {
            for (OwnedPokemon pkmn : getPlayer().getTeam()) {
                if (!pkmn.isFainted())
                    allFainted = false;
            }

            if (allFainted) {
                running = false;
                defeat();
            } else {
                nodeMan.showTeamPane(getPlayer());
                nodeMan.showChangePkmnView();
                cancelChangePkmnBtn.setVisible(false);
                running = false;
                waitForPkmnChange = true;
            }

        } else if (opponentPokemon.isFainted()) {
            running = false;
            victory();
        }
    }

    /**
     * A seguito della sconfitta del giocatore, viene controllata l'eventuale presenza di pokemon nel
     * team ancora in grado di lottare. In caso negativo si torna alla modalità arcade.
     */
    private void defeat() {
        delay3_2_0.setOnFinished(e2 -> {
            endGame(false);
        });

        delay2_2_0.setOnFinished(e2 -> {
            if (checkOtherPokemonAvailable()) {
                nodeMan.updateLogLbl("Hai altri pokemon disponibili");
            } else {
                nodeMan.updateLogLbl("Non hai altri pokemon disponibili");
                delay3_2_0.play();
            }
        });

        delay_2_0.setOnFinished(e1 -> {
            nodeMan.updateLogLbl(playerPokemon.getName() + " è esausto!");
            playerPkmnGif.setVisible(false);
            delay2_2_0.play();
        });

        delay_2_0.play();
    }

    /**
     * A seguito della sconfitta del pokemon avversario vengono assegnati i punti XP ed EV.
     * L'eventuale aumento di livello del pokemon viene segnalato con un messaggio nella
     * log label.
     */
    private void victory() {
        soundMan.switchTrack("mp3.victory");

        int gainedXP = (opponentPokemon.getBreed().baseValueOf(Stats.XP) * opponentPokemon.getLevel()) / 6;
        int oldLevel = playerPokemon.getLevel();
        playerPokemon.increaseEV(opponentPokemon);

        // Termina la lotta
        delay3_2_0.setOnFinished(e -> {
            endGame(true);
        });

        // Schermata di aggiunta nuova mossa
        delay4_2_0.setOnFinished(e -> {
            nodeMan.showNewMovePane(playerPokemon, newMove);
        });

        delay_1_5.setOnFinished(e -> {
            nodeMan.updateLogLbl(playerPokemon.getName() + " è salito al livello " + playerPokemon.getLevel() + "!");

            newMove = PokemonManager.getNewMove(playerPokemon);
            if (newMove != null) {
                if (playerPokemon.getMoves().size() < 4) { // se le mosse sono meno di 4
                    playerPokemon.addMove(newMove);
                    delay3_2_0.play(); // Termina la lotta
                } else
                    delay4_2_0.play(); // Schermata di aggiunta nuova mossa
            }
            else
                delay3_2_0.play(); // Termina la lotta
        });

        delay2_2_0.setOnFinished(e -> {
            playerPokemon.increaseXP(gainedXP);
            nodeMan.updateLogLbl(playerPokemon.getName() + " ha ottenuto " + gainedXP + "xp!");
            if (playerPokemon.getLevel() > oldLevel)
                delay_1_5.play();
            else
                delay3_2_0.play();
        });

        delay_2_0.setOnFinished(e -> {
            nodeMan.updateLogLbl(opponentPokemon.getName() + " è esausto!");
            opponentPkmnGif.setVisible(false);
            delay2_2_0.play();
        });

        delay_2_0.play();
    }

    /**
     * Metodo di fine gioco. In base al valore del parametro victory, il giocatore viene riportato o all'ultima
     * area visitata (vittoria) o a casa sua nel soggiorno, curando tutto il team (sconfitta).
     *
     * @param victory Valore booleano che specifica se si sta chiudendo con una vittoria o con una sconfitta
     */
    private void endGame(boolean victory) {
        if (victory){
            getPlayer().goToLastSafeArea();
            getPlayer().updateNarratorTextHistory(String.format("Complimenti, hai sconfitto %s!", opponentPokemon.getName()));
        } else {
            getPlayer().setCurrentArea("home_living_room");
            getPlayer().updateNarratorTextHistory(String.format("Sei stato sconfitto da %s... sei tornato a casa per curare la tua scuadra.", opponentPokemon.getName()));
            getPlayer().healTeam();
        }
        switchToArcadeScene();
    }

    /**
     * Controllata l'eventuale presenza di altri pokemon (tra quelli nel team del giocatore) in grado di lottare.
     *
     * @return Valore booleano che specifica la presenza o meno di altri pokemon disponibili tra i pokemon posseduti dall'utente
     */
    private boolean checkOtherPokemonAvailable() {
        for (Pokemon pkmn : getPlayer().getTeam()) {
            if (!pkmn.isFainted())
                return true;
        }
        return false;
    }

    /**
     * Controlla che la mossa sia disponibile all'utlizzo (PP > 0).
     *
     * @param pkmn Pokemon che sta selezionando la mossa
     * @param moveIndex Indice della mossa che si vuole selezionare (tra le 4 del pokemon)
     * @return Valore booleano che specidica se la mossa è disponibile (ha abbastanza PP)
     */
    private boolean isMoveAvailable(Pokemon pkmn, int moveIndex) {
        return (pkmn.getPP(moveIndex) > 0 && pkmn.getCurrHP() > 0);
    }

    /**
     * Selezionata randomicamente una tra le mosse disponibili dell'avversario e ritornato il suo indice.
     *
     * @return Genera randomicamente l'indice di una mossa tra le mosse disponibili
     */
    private int getOpponentMoveIndex(){
        int mvSize = opponentPokemon.getMoves().size();
        int moveIndex;

        do moveIndex = rand.nextInt(0, mvSize);
        while (!isMoveAvailable(opponentPokemon, moveIndex));

        return moveIndex;
    }

    /**
     * A partire dal nome identificativo del pannello della mossa (move_0, move_1, move_2, move_3),
     * estrae l'indice della mossa relativa.
     *
     * @param name Nome identificativo del pannello della mossa selezionata
     * @return Indice della mossa
     */
    private int getMoveIndexFromPaneID(String name) {
        return Integer.parseInt(name.substring(name.length()-1));
    }

    /**
     * Aggiorna la variabile nextAttacker (contenente una delle due istanze di pokemon tra quello
     * dell'utente e quello dell'avversario) con il primo pokemon che deve attaccare nel turno corrente.
     * L'ordine viene deciso prima in base alla priorità della mossa e in caso di parità in
     * base alla velocità del pokemon. A velocità uguale attacca prima l'avversario.
     *
     * @param plMvIndex Indice della mossa (tra le 4) selezionata dal giocatore
     * @param oppMvIndex Indice della mossa (tra le 4) selezionata dall'avversario
     */
    private void updateNextAttacker(int plMvIndex, int oppMvIndex) {
        if (plMvIndex == -1) {
            nextAttacker = Turn.OPPONENT;
            return;
        } else if (oppMvIndex == -1){
            nextAttacker = Turn.PLAYER;
            return;
        }

        int playerPriority = playerPokemon.getMove(plMvIndex).getPriority();
        int opponentPriority = opponentPokemon.getMove(oppMvIndex).getPriority();

        if (playerPriority == opponentPriority) {
            if (getStat(playerPokemon, Stats.SPEED) > getStat(opponentPokemon, Stats.SPEED)) {
                nextAttacker = Turn.PLAYER;
            } else {
                nextAttacker = Turn.OPPONENT;
            }
        } else if (playerPriority > opponentPriority) {
            nextAttacker = Turn.PLAYER;
        } else {
            nextAttacker = Turn.OPPONENT;
        }
    }

    /**
     * Passa alla scena di arcade.
     */
    private void switchToArcadeScene() {
        try {
            FXMLLoader loader = SceneManager.switchScene(rootPane, "fxml.arcade", "css.arcade");
            NodeManager.setRoot(loader.getRoot());
            ArcadeController arcadeCtrl = loader.getController();
            ArcadeNodeManager.setController(arcadeCtrl);
            arcadeCtrl.initializeScene();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
