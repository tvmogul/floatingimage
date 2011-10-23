package dk.nindroid.rss.renderers.floating.positionControllers;

import dk.nindroid.rss.Display;
import dk.nindroid.rss.MainActivity;
import dk.nindroid.rss.gfx.Vec3f;
import dk.nindroid.rss.renderers.floating.FloatingRenderer;

public class Gallery extends PositionController {
	public static final float  	mFloatZ = -3.5f;
	
	MainActivity mActivity;
	Display mDisplay;
	float mYLayerPos = 0;
	
	// Return types - avoid creating new objects
	Vec3f mJitter;
	Vec3f mPos;
	float mRotation;
	final float mSpacing;
	final int mImageId;

	private final int mNoImages;
	
	public Gallery(MainActivity activity, Display display, int image, int noImages){
		mSpacing = 1.0f / (noImages / 3.0f);
		this.mNoImages = noImages;
		this.mImageId = image;
		this.mDisplay = display;
		this.mActivity = activity;
		mJitter = new Vec3f();
		mPos = new Vec3f();
		switch(image % 3){
			case 0: mYLayerPos = 0.0f; break;	
			case 1: mYLayerPos = 1.5f; break;
			case 2: mYLayerPos = -1.5f; break;
		}
		jitter();
	}
	
	@Override
	public void jitter() {
	}

	@Override
	public float getOpacity(float interval) {
		return 1;
	}

	@Override
	public Vec3f getPosition(float interval) {
		float farRight = getFarRight();
		mPos.setX(farRight - (interval * farRight * 2) + mJitter.getX());
		mPos.setY(mYLayerPos * mDisplay.getFocusedHeight() + mJitter.getY());
		mPos.setZ(mFloatZ + mJitter.getZ());	
		return mPos;
	}


	@Override
	public void getRotation(float interval, Rotation a, Rotation b) {
		a.setAngle(0.0f);
		b.setAngle(0.0f);
	}

	public float getFarRight(){
		return mDisplay.getWidth() * 0.7f * (-mFloatZ + FloatingRenderer.mJitterZ) * 1.2f + 1.3f;
	}

	@Override
	public void getGlobalOffset(float x, float y, Vec3f out) {
		if(y*y > x*x*x*x){
			out.setY(y / 100.0f);
		}
	}

	@Override
	public float getTimeAdjustment(float speedX, float speedY) {
		return -speedX;
	}
	
	@Override
	public float getScale() {
		return 0.75f;
	}

	@Override
	public float adjustInterval(float interval) {
		float spacing = 1.0f / (mNoImages / 3.0f);
		int row = mImageId / 3;
		return (interval - spacing * row + 1) % 1;
	}
}
