package rendering_tests;
/*
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
*/
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.Font;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.jtable_adjustment.renderers.FontCell;
import ui.jtable_adjustment.renderers.TableColors;
import ui.jtable_adjustment.renderers.background.BG_Green;
import ui.jtable_adjustment.renderers.foreground.FG_White;

class TableColorsTests {
  private final Font defaultfont = new Font("Verdana", Font.PLAIN, 11);
  private TableColors fontcell;
  private TableColors background;
  private TableColors foreground;
  private TableColors backforefont;
  private TableColors forebackfont;
  private Color bgcolor;
  private Color fgcolor;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    fontcell = new FontCell(defaultfont);
    background = new BG_Green(fontcell);
    bgcolor = Color.GREEN;
    foreground = new FG_White(fontcell);
    fgcolor = Color.WHITE;

    backforefont = new BG_Green(new FG_White(fontcell));
    forebackfont = new FG_White(new BG_Green(fontcell));
  }

  @AfterEach
  void tearDown() throws Exception {}


  /* ---------------------------------------- FontCell tests ---------------------------------------- */
  @Test
  void fontCellUnrendered_fontShouldBeEqual() {
    assertTrue(fontcell.getFont().equals(defaultfont));
  }
  @Test
  void fontCellRendered_fontShouldBeEqual() {
    fontcell.render();
    assertTrue(fontcell.getFont().equals(defaultfont));
  }
  @Test
  void fontCell_getBGShouldThrowException() {
    assertThrows(UnsupportedOperationException.class, () -> { fontcell.getBG(); });
    fontcell.render();
    assertThrows(UnsupportedOperationException.class, () -> { fontcell.getBG(); });
  }
  @Test
  void fontCell_getFGShouldThrowException() {
    assertThrows(UnsupportedOperationException.class, () -> { fontcell.getFG(); });
    fontcell.render();
    assertThrows(UnsupportedOperationException.class, () -> { fontcell.getFG(); });
  }


  /* ---------------------------------------- background + fontcell tests ---------------------------------------- */
  @Test
  void backgroundUnrendered_fontShouldBeEqual() {
    assertTrue(background.getFont().equals(defaultfont));
  }
  @Test
  void backgroundRendered_fontShouldBeEqual() {
    background.render();
    assertTrue(background.getFont().equals(defaultfont));
  }

  @Test
  void backgroundUnrendered_backgroundShouldBeNull() {
    assertNull(background.getBG());
  }
  @Test
  void backgroundRendered_backgroundShouldBeEqual() {
    background.render();
    assertEquals(bgcolor,background.getBG());
  }

  @Test
  void background_getFGShouldThrowException() {
    assertThrows(UnsupportedOperationException.class, () -> { background.getFG(); });
    background.render();
    assertThrows(UnsupportedOperationException.class, () -> { background.getFG(); });
  }


  /* ---------------------------------------- foreground + fontcell tests ---------------------------------------- */
  @Test
  void foregroundUnrendered_fontShouldBeEqual() {
    assertTrue(foreground.getFont().equals(defaultfont));
  }
  @Test
  void foregroundRendered_fontShouldBeEqual() {
    foreground.render();
    assertTrue(foreground.getFont().equals(defaultfont));
  }

  @Test
  void forekgroundUnrendered_foregroundShouldBeNull() {
    assertNull(foreground.getFG());
  }
  @Test
  void foregroundRendered_foregroundShouldBeEqual() {
    foreground.render();
    assertEquals(fgcolor,foreground.getFG());
  }

  @Test
  void foreground_getBGShouldThrowException() {
    assertThrows(UnsupportedOperationException.class, () -> { foreground.getBG(); });
    foreground.render();
    assertThrows(UnsupportedOperationException.class, () -> { foreground.getBG(); });
  }


  /* ---------------------------------------- background + foreground + fontcell tests ---------------------------------------- */
  /* ---------------------------------------- foreground + background + fontcell tests ---------------------------------------- */
  @Test
  void groundsUnrendered_fontShouldBeEqual() {
    assertTrue(backforefont.getFont().equals(defaultfont));

    assertTrue(forebackfont.getFont().equals(defaultfont));
  }
  @Test
  void groundsRendered_fontShouldBeEqual() {
    backforefont.render();
    assertTrue(backforefont.getFont().equals(defaultfont));

    forebackfont.render();
    assertTrue(forebackfont.getFont().equals(defaultfont));
  }

  @Test
  void groundsUnrendered_backgroundShouldBeNull() {
    assertNull(backforefont.getBG());

    assertNull(forebackfont.getBG());
  }
  @Test
  void groundsRendered_backgroundShouldBeEqual() {
    backforefont.render();
    assertEquals(bgcolor,backforefont.getBG());

    forebackfont.render();
    assertEquals(bgcolor,forebackfont.getBG());
  }

  @Test
  void groundsUnrendered_foregroundShouldBeNull() {
    assertNull(backforefont.getFG());

    assertNull(forebackfont.getFG());
  }
  @Test
  void groundsRendered_foregroundShouldBeEqual() {
    backforefont.render();
    assertEquals(fgcolor,backforefont.getFG());

    forebackfont.render();
    assertEquals(fgcolor,forebackfont.getFG());
  }
}








