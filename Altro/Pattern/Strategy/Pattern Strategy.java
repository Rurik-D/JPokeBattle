import java.util.List;
import java.util.ArrayList;

// Dimostrazione del pattern strategy.

// ARCHITETTURA PROGETTO
//
// Ho un gestore di voti, voglio registrare i voti di un esame.
// Quando un voto viene verbalizzato, voglio inviare una notifica
// allo studente.
//
// Gli studenti possono impostare un metodo preferito per ricevere
// le notifiche: via mail, sms, o notifica in app.

public class SegreteriaUniversitaria {
    private List<Studente> studenti = new ArrayList<>();

    public SegreteriaUniversitaria() {
        // Tre studenti diversi che ricevono notifiche con mezzi diversi.
        studenti.add(new Studente(new MetodoNotificaEmail("mario@rossi.it")));
        studenti.add(new Studente(new MetodoNotificaSms("3325588246")));
        studenti.add(new Studente(new MetodoNotificaApp()));
    }

    public void verbalizzaEsame() {
        studenti.get(0).assegnaVoto(24);
        studenti.get(1).assegnaVoto(28);
        studenti.get(2).assegnaVoto(17);

        // DOMANDA (dopo aver letto e capito il resto del codice)
        //
        // Cosa e dove ricevono i tre studenti, secondo il codice di verbalizzaEsame?
    }
}

public class Studente {
    private MetodoNotifica metodoDiNotifica;
    // altri campi: ultimi voti, media dei voti ecc.

    // Da chi viene chiamato? Cioè, chi è che decide il metodo
    // di notifica? In questo esempio, è la classe SegreteriaUniversitaria
    // a creare gli studenti.
    public Studente(MetodoNotifica metodoDiNotifica) {
        this.metodoDiNotifica = metodoDiNotifica;
    }

    public void assegnaVoto(int voto) {
        // Con che metodo stiamo inviando la notifica? Email? SMS? App?
        //
        // Questo è il punto: la classe Studente non lo sa!
        // Ma non interessa, quello che conta è che stiamo inviando una notifica,
        // non ci interessa come.
        if (voto >= 18) {
            metodoDiNotifica.notifica("Hai passato l'esame! Voto: %d".formatted(voto));
        } else {
            metodoDiNotifica.notifica("Rinuncia esame!");
        }
    }

    // Da qui in poi:
    // !!! Versione senza pattern strategy. Molto brutta! !!!
    // (Faccio un unico messaggio per brevità)
    // Se questa parte ti dà fastidio, cancella fino a "FINE versione brutta".

    // Questo campo non esiste nella versione bella.
    // Scelgo un nome diverso, non uso enum per non appesantire il codice per
    // questo esempio.
    private String metodoPreferito;

    private void assegnaVotoBrutto(int voto) {
        // OSSERVAZIONE: adesso alla classe Studente INTERESSA quali sono
        // i metodi di notifica! E se poi volessi aggiungere notifica via
        // posta ordinaria? Dovrei aggiungere un ramo qui.
        if (metodoPreferito.equals("email")) {
            // invia email
        } else if (metodoPreferito.equals("sms")) {
            // invia sms
        } else {
            // invia app
        }
    }

    // FINE versione brutta
}

// Definisce il modo (nello specifico, il mezzo) in cui
// uno studente può ricevere notifiche.
public interface MetodoNotifica {
    void notifica(String messaggio);
}

// Queste sono le implementazioni dei metodi di notifica. Ogni
// studente avrà l'implementazione che più preferisce.

public class MetodoNotificaEmail implements MetodoNotifica {
    private String indirizzo;

    public MetodoNotificaEmail(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public void notifica(String messaggio) {
        // invia il messaggio via email
    }
}

public class MetodoNotificaSms implements MetodoNotifica {
    private String numero;

    public MetodoNotificaSms(String numero) {
        this.numero = numero;
    }

    @Override
    public void notifica(String messaggio) {
        // invia il messaggio via sms
    }
}

public class MetodoNotificaApp implements MetodoNotifica {
    @Override
    public void notifica(String messaggio) {
        // invia il messaggio nell'app come notifica
    }
}
