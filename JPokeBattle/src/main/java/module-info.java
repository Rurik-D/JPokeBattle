module it.rd.jpokebattle {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.web;
    requires transitive javafx.graphics;

    requires transitive org.controlsfx.controls;
    requires transitive com.dlsc.formsfx;
    requires transitive net.synedra.validatorfx;
    requires transitive org.kordamp.ikonli.javafx;
    requires transitive org.kordamp.bootstrapfx.core;
    requires transitive eu.hansolo.tilesfx;
    requires transitive com.almasb.fxgl.all;
    requires transitive javafx.media;
    requires transitive com.google.gson;
    requires transitive java.desktop;
    requires annotations;

    exports it.rd.jpokebattle;
    opens it.rd.jpokebattle;

    exports it.rd.jpokebattle.controller;
    opens it.rd.jpokebattle.controller;

    exports it.rd.jpokebattle.model;
    opens it.rd.jpokebattle.model;

    exports it.rd.jpokebattle.view;
    opens it.rd.jpokebattle.view;

    exports it.rd.jpokebattle.model.pokemon;
    opens it.rd.jpokebattle.model.pokemon;

    exports it.rd.jpokebattle.util.audio;
    opens it.rd.jpokebattle.util.audio;

    exports it.rd.jpokebattle.util.file;
    opens it.rd.jpokebattle.util.file;

    exports it.rd.jpokebattle.controller.arcade;
    opens it.rd.jpokebattle.controller.arcade;

    exports it.rd.jpokebattle.controller.menu;
    opens it.rd.jpokebattle.controller.menu;

    exports it.rd.jpokebattle.model.profile;
    opens it.rd.jpokebattle.model.profile;

    exports it.rd.jpokebattle.model.area;
    opens it.rd.jpokebattle.model.area;
    exports it.rd.jpokebattle.view.menu;
    opens it.rd.jpokebattle.view.menu;
    exports it.rd.jpokebattle.view.arcade;
    opens it.rd.jpokebattle.view.arcade;
}