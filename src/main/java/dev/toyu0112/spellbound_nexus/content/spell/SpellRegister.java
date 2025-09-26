package dev.toyu0112.spellbound_nexus.content.spell;

import dev.toyu0112.spellbound_nexus.content.spell.astrovoid.AstrovoidMeteorSpell;

import java.util.List;

public class SpellRegister {
    public static List<Spell> getAllSpells() {
        return List.of(
                new AstrovoidMeteorSpell()
        );
    }

    public static void spellRegister() {
        getAllSpells().forEach(SpellManager::register);
    }
}
