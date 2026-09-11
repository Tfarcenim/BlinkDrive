package tfar.blinkdrive.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import tfar.blinkdrive.Constants;
import tfar.blinkdrive.Init;

public class BDLang extends LanguageProvider {
    public BDLang(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(Init.BLOCK,"Blink Drive");
    }
}
