package rendering_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.awt.Color;
import java.awt.Font;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.rendering.renderers.BG_Green;
import ui.rendering.renderers.BasicCell_Font;
import ui.rendering.renderers.TableColors;

class TableColorsTests {
  private TableColors tc;
  private final Font defaultfont = new Font("Verdana", Font.PLAIN, 11);

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    tc = new BasicCell_Font(defaultfont);
  }

  @AfterEach
  void tearDown() throws Exception {}

  @Test
  void basicCellFontUnrendered_superMembersShouldEqualNull() {
    assertNull(tc.getBG());
    assertNull(tc.getFG());
  }

  @Test
  void basicCellFontUnrendered_fontShouldBeEqual() {
    assertTrue(tc.getFont().equals(defaultfont));
  }

  @Test
  void basicCellFontRendered_superMembersShouldBeEqual() {
    tc.render();
    assertEquals(Color.WHITE,tc.getBG());
    assertEquals(Color.BLACK,tc.getFG());
  }

  @Test
  void BG_GreenUnrendered_membersShouldEqualNull() {
    assertNull(tc.getBG());
    assertNull(tc.getFG());

    tc = new BG_Green(tc);

    assertNull(tc.getBG());
    assertNull(tc.getFG());
  }

  @Test
  void BG_GreenUnrendered_nestedFontShouldNotEqualNull() {
    assertNotNull(tc.getFont());

    tc = new BG_Green(tc);

    assertNotNull(tc.getFont());
  }

  @Test
  void BG_GreenRendered_membersShouldBeEqual() {
    tc.render();
    assertEquals(Color.WHITE,tc.getBG());
    assertEquals(Color.BLACK,tc.getFG());
    assertTrue(tc.getFont().equals(defaultfont));

    tc = new BG_Green(tc);

    assertEquals(Color.WHITE,tc.getBG());
    assertEquals(Color.BLACK,tc.getFG());
    assertTrue(tc.getFont().equals(defaultfont));

    tc.render();
    assertEquals(Color.GREEN,tc.getBG());
    assertEquals(Color.BLACK,tc.getFG());
    assertTrue(tc.getFont().equals(defaultfont));

  }
}








