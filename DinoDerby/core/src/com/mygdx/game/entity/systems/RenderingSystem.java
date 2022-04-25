package com.mygdx.game.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.LevelFactory;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entity.components.GhostComponent;
import com.mygdx.game.entity.components.PlayerComponent;
import com.mygdx.game.entity.components.TextureComponent;
import com.mygdx.game.entity.components.TransformComponent;

import java.util.Comparator;
import java.util.Map;

public class RenderingSystem extends SortedIteratingSystem {

    // convert pixel per meter
    static final float PPM = 4.0f;

    // height and with of camera
    static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth()/PPM;
    static final float FRUSTUM_HEIGHT = Gdx.graphics.getWidth()/PPM;

    public static final float PIXEL_TO_METERS = 1.0f / PPM;


    /*
    public RenderingSystem(Family family, Comparator<Entity> comparator) {
        super(family, comparator);
    }
    */

    public static float PixelToMeters(float pixelValue) {
        return pixelValue * PIXEL_TO_METERS;
    }

    private MyGdxGame game;
    private SpriteBatch sb;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera cam;
    private OrthogonalTiledMapRenderer mapRenderer;

    private ComponentMapper<TextureComponent> cmTexture;
    private ComponentMapper<TransformComponent> cmTransform;
    private ComponentMapper<GhostComponent> cmGhost;
//    private ComponentMapper<PlayerComponent> cmPlayer;

    @SuppressWarnings("unchecked")
    public RenderingSystem(SpriteBatch sb, TiledMap map, MyGdxGame game) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get(), new ZComparator());

        comparator = new ZComparator();
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);
        // component mappers
        cmTexture = ComponentMapper.getFor(TextureComponent.class);
        cmTransform = ComponentMapper.getFor(TransformComponent.class);
        cmGhost = ComponentMapper.getFor(GhostComponent.class);
//        cmPlayer = ComponentMapper.getFor(PlayerComponent.class);
        // array for rendering entities
        renderQueue = new Array<Entity>();

        this.sb = sb;
        this.game = game;

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT/ 2f, 0);

        System.out.println("Created rendering system");
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        renderQueue.sort(comparator);

        mapRenderer.setView(cam);
        mapRenderer.render();

        cam.update();
        sb.setProjectionMatrix(cam.combined);
        sb.enableBlending();
        sb.begin();

        for (Entity entity : renderQueue) {
            TextureComponent texture = cmTexture.get(entity);
            TransformComponent transform = cmTransform.get(entity);

            if (texture.region == null) {
                continue;
            }

            float width = texture.region.getRegionWidth();
            float height = texture.region.getRegionHeight();

            float originX = width / 2f;
            float originY = height / 2f;

//            //Posting player position to firebase
//            System.out.println("I rendering system");
//            System.out.println(entity);
//            System.out.println(cmPlayer.has(entity));
//            if (entity != null && cmPlayer.has(entity)) {
//                game.getFirebaseInstance().updatePlayerInGame(game.getCurrGameID(), game.getPlayerID(), transform.position.x, transform.position.y);
//            }

            Color c = sb.getColor();
            if (cmGhost.has(entity)) {
                //Changing opacity if entity is ghost
                sb.setColor(c.r, c.g, c.b, 0.75f);

                //Checking and setting position from firebase database
                GhostComponent ghostComponent = cmGhost.get(entity);
                Map<String, Float> playerMap = game.getPlayers().get(ghostComponent);
                transform.position.x = playerMap.get("xPos");
                transform.position.y = playerMap.get("yPos");

            }
            sb.draw(texture.region,
                    transform.position.x - originX, transform.position.y -originY,
                    originX, originY,
                    width, height,
                    PixelToMeters(transform.scale.x), PixelToMeters(transform.scale.y),
                    transform.rotation);
        }
        sb.end();
        renderQueue.clear();

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return cam;
    }
}
