{
	"format_version": "1.12.0",
	"minecraft:geometry": [
		{
			"description": {
				"identifier": "geometry.shuttle_engine",
				"texture_width": 16,
				"texture_height": 16,
				"visible_bounds_width": 2,
				"visible_bounds_height": 3.5,
				"visible_bounds_offset": [0, 1.25, 0]
			},
			"bones": [
				{
					"name": "thruster_center",
					"pivot": [0, 0, 0],
					"cubes": [
						{
							"origin": [-2, 1, -2],
							"size": [4, 3, 4],
							"uv": {
								"north": {"uv": [6, 13], "uv_size": [4, 3]},
								"east": {"uv": [6, 13], "uv_size": [4, 3]},
								"south": {"uv": [6, 13], "uv_size": [4, 3]},
								"west": {"uv": [6, 13], "uv_size": [4, 3]},
								"up": {"uv": [10, 10], "uv_size": [-4, -4]},
								"down": {"uv": [10, 10], "uv_size": [-4, -4]}
							}
						},
						{
							"origin": [-1, 4, -1],
							"size": [2, 1, 2],
							"uv": {
								"north": {"uv": [7, 12], "uv_size": [2, 1]},
								"east": {"uv": [7, 12], "uv_size": [2, 1]},
								"south": {"uv": [7, 12], "uv_size": [2, 1]},
								"west": {"uv": [7, 12], "uv_size": [2, 1]}
							}
						},
						{
							"origin": [-2, 5, -2],
							"size": [4, 3, 4],
							"uv": {
								"north": {"uv": [6, 9], "uv_size": [4, 3]},
								"east": {"uv": [6, 9], "uv_size": [4, 3]},
								"south": {"uv": [6, 9], "uv_size": [4, 3]},
								"west": {"uv": [6, 9], "uv_size": [4, 3]},
								"down": {"uv": [10, 10], "uv_size": [-4, -4]}
							}
						}
					]
				},
				{
					"name": "thruster_north",
					"pivot": [0, 0, 0],
					"cubes": [
						{
							"origin": [-2, 0, -7],
							"size": [4, 4, 4],
							"uv": {
								"north": {"uv": [6, 13], "uv_size": [4, 3]},
								"east": {"uv": [11, 13], "uv_size": [4, 3]},
								"south": {"uv": [6, 13], "uv_size": [4, 3]},
								"west": {"uv": [1, 13], "uv_size": [4, 3]},
								"up": {"uv": [10, 5], "uv_size": [-4, -4]},
								"down": {"uv": [10, 15], "uv_size": [-4, -4]}
							}
						},
						{
							"origin": [-1, 4, -6],
							"size": [2, 1, 2],
							"uv": {
								"north": {"uv": [7, 12], "uv_size": [2, 1]},
								"east": {"uv": [12, 12], "uv_size": [2, 1]},
								"south": {"uv": [7, 12], "uv_size": [2, 1]},
								"west": {"uv": [2, 12], "uv_size": [2, 1]}
							}
						},
						{
							"origin": [-2, 5, -7],
							"size": [4, 3, 4],
							"uv": {
								"north": {"uv": [6, 9], "uv_size": [4, 3]},
								"east": {"uv": [11, 9], "uv_size": [4, 3]},
								"south": {"uv": [6, 9], "uv_size": [4, 3]},
								"west": {"uv": [1, 9], "uv_size": [4, 3]},
								"down": {"uv": [10, 15], "uv_size": [-4, -4]}
							}
						}
					]
				},
				{
					"name": "thruster_east",
					"pivot": [0, 0, 0],
					"cubes": [
						{
							"origin": [-7, 0, -2],
							"size": [4, 4, 4],
							"uv": {
								"north": {"uv": [1, 13], "uv_size": [4, 3]},
								"east": {"uv": [6, 13], "uv_size": [4, 3]},
								"south": {"uv": [11, 13], "uv_size": [4, 3]},
								"west": {"uv": [6, 13], "uv_size": [4, 3]},
								"up": {"uv": [15, 10], "uv_size": [-4, -4]},
								"down": {"uv": [15, 10], "uv_size": [-4, -4]}
							}
						},
						{
							"origin": [-7, 5, -2],
							"size": [4, 3, 4],
							"uv": {
								"north": {"uv": [1, 9], "uv_size": [4, 3]},
								"east": {"uv": [6, 9], "uv_size": [4, 3]},
								"south": {"uv": [11, 9], "uv_size": [4, 3]},
								"west": {"uv": [6, 9], "uv_size": [4, 3]},
								"down": {"uv": [15, 10], "uv_size": [-4, -4]}
							}
						},
						{
							"origin": [-6, 4, -1],
							"size": [2, 1, 2],
							"uv": {
								"north": {"uv": [2, 12], "uv_size": [2, 1]},
								"east": {"uv": [7, 12], "uv_size": [2, 1]},
								"south": {"uv": [12, 12], "uv_size": [2, 1]},
								"west": {"uv": [7, 12], "uv_size": [2, 1]}
							}
						}
					]
				},
				{
					"name": "thruster_south",
					"pivot": [0, 0, 0],
					"cubes": [
						{
							"origin": [-2, 0, 3],
							"size": [4, 4, 4],
							"uv": {
								"north": {"uv": [6, 13], "uv_size": [4, 3]},
								"east": {"uv": [1, 13], "uv_size": [4, 3]},
								"south": {"uv": [6, 13], "uv_size": [4, 3]},
								"west": {"uv": [11, 13], "uv_size": [4, 3]},
								"up": {"uv": [10, 15], "uv_size": [-4, -4]},
								"down": {"uv": [10, 5], "uv_size": [-4, -4]}
							}
						},
						{
							"origin": [-1, 4, 4],
							"size": [2, 1, 2],
							"uv": {
								"north": {"uv": [7, 12], "uv_size": [2, 1]},
								"east": {"uv": [2, 12], "uv_size": [2, 1]},
								"south": {"uv": [7, 12], "uv_size": [2, 1]},
								"west": {"uv": [12, 12], "uv_size": [2, 1]}
							}
						},
						{
							"origin": [-2, 5, 3],
							"size": [4, 3, 4],
							"uv": {
								"north": {"uv": [6, 9], "uv_size": [4, 3]},
								"east": {"uv": [1, 9], "uv_size": [4, 3]},
								"south": {"uv": [6, 9], "uv_size": [4, 3]},
								"west": {"uv": [11, 9], "uv_size": [4, 3]},
								"down": {"uv": [10, 5], "uv_size": [-4, -4]}
							}
						}
					]
				},
				{
					"name": "thruster_west",
					"pivot": [0, 0, 0],
					"cubes": [
						{
							"origin": [3, 0, -2],
							"size": [4, 4, 4],
							"uv": {
								"north": {"uv": [11, 13], "uv_size": [4, 3]},
								"east": {"uv": [6, 13], "uv_size": [4, 3]},
								"south": {"uv": [1, 13], "uv_size": [4, 3]},
								"west": {"uv": [6, 13], "uv_size": [4, 3]},
								"up": {"uv": [5, 10], "uv_size": [-4, -4]},
								"down": {"uv": [5, 10], "uv_size": [-4, -4]}
							}
						},
						{
							"origin": [4, 4, -1],
							"size": [2, 1, 2],
							"uv": {
								"north": {"uv": [12, 12], "uv_size": [2, 1]},
								"east": {"uv": [7, 12], "uv_size": [2, 1]},
								"south": {"uv": [2, 12], "uv_size": [2, 1]},
								"west": {"uv": [7, 12], "uv_size": [2, 1]}
							}
						},
						{
							"origin": [3, 5, -2],
							"size": [4, 3, 4],
							"uv": {
								"north": {"uv": [11, 9], "uv_size": [4, 3]},
								"east": {"uv": [6, 9], "uv_size": [4, 3]},
								"south": {"uv": [1, 9], "uv_size": [4, 3]},
								"west": {"uv": [6, 9], "uv_size": [4, 3]},
								"down": {"uv": [5, 10], "uv_size": [-4, -4]}
							}
						}
					]
				},
				{
					"name": "bone",
					"pivot": [0, 0, 0],
					"cubes": [
						{
							"origin": [-7, 8, -7],
							"size": [14, 1, 14],
							"uv": {
								"north": {"uv": [1, 8], "uv_size": [14, 1]},
								"east": {"uv": [1, 8], "uv_size": [14, 1]},
								"south": {"uv": [1, 8], "uv_size": [14, 1]},
								"west": {"uv": [1, 8], "uv_size": [14, 1]},
								"down": {"uv": [15, 15], "uv_size": [-14, -14]}
							}
						},
						{
							"origin": [-8, 9, -8],
							"size": [16, 16, 16],
							"uv": {
								"north": {"uv": [0, 0], "uv_size": [16, 16]},
								"east": {"uv": [0, 0], "uv_size": [16, 16]},
								"south": {"uv": [0, 0], "uv_size": [16, 16]},
								"west": {"uv": [0, 0], "uv_size": [16, 16]},
								"up": {"uv": [16, 16], "uv_size": [-16, -16]},
								"down": {"uv": [16, 16], "uv_size": [-16, -16]}
							}
						}
					]
				}
			]
		}
	]
}